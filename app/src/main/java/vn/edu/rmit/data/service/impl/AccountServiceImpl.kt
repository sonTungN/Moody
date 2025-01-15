package vn.edu.rmit.data.service.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import vn.edu.rmit.data.model.Profile
import vn.edu.rmit.data.model.type.Role
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.RoleService
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val roleService: RoleService,
) : AccountService {
    private val profileRef = db.collection("profiles")

    override val currentUserId: String?
        get() = auth.currentUser?.uid

    private var currentUserRole: String? = null

    override val authenticated: Boolean
        get() = auth.currentUser !== null

    override suspend fun documentToProfile(document: DocumentSnapshot): Profile {

        return Profile(
            id = document.id,
            fullName = document.getString("name") ?: "",
            email = document.getString("email") ?: "",
            phoneNumber = document.getString("phoneNumber") ?: "",
            profileUrl = document.getString("profileUrl") ?: "",
            role = document
                .getDocumentReference("role")
                ?.snapshots()
                ?.map {
                    roleService.documentToRole(it)
                }?.first() ?: Role(),
            booking = document.get("booking") as? List<String> ?: emptyList(),
            ownedProperties = document.get("ownedProperties") as? List<String> ?: emptyList(),
            savedProperties = document.get("savedProperties") as? List<String> ?: emptyList()
        )
    }

    override suspend fun authenticate(
        email: String, password: String, onSuccess: (role: String) -> Unit
    ): Result<Unit> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: throw Exception("Authentication failed")

            val profileDoc = db.collection("profiles")
                .document(user.uid)
                .get()
                .await()

            currentUserRole = profileDoc.getDocumentReference("role")?.id
            onSuccess(currentUserRole ?: "unknown")

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(
        email: String, password: String, profile: Profile
    ) : Result<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            createProfile(currentUserId!!, profile)
            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun logout(onSuccess: () -> Unit) {
        currentUserRole = null
        auth.signOut()
        onSuccess()
    }

    override suspend fun getProfile(): Profile? = auth.currentUser?.let {
        profileRef.document(it.uid).get().await().let { documentToProfile(
            it) }
    }

    override suspend fun getProfileById(id: String): Profile {
        return db.collection("profiles").document(id).get().await().let {
            documentToProfile(it)
        }
    }

    override fun getProfileFlow(): Flow<List<Profile>> {
        return db.collection("profiles").snapshots().map {
            it.documents.map { documentToProfile(it) }
        }
    }

    override suspend fun createProfile(id: String, profile: Profile): String {
        db.collection("profiles").document(id).set(
            hashMapOf(
                "name" to profile.fullName,
                "role" to profile.role.let { db.collection("roles").document(it.id) },
            )
        )
        return id
    }

    override suspend fun getProfileCount(): Long {
        return db.collection("profiles").count().get(AggregateSource.SERVER).await().count
    }

    override suspend fun updateProfile(profile: Profile) {
        val profileData = hashMapOf(
            "name" to profile.fullName,
            "role" to profile.role.let { db.collection("roles").document(it.id) }, // Convert Role to DocumentReference
            "booking" to profile.booking,
            "ownedProperties" to profile.ownedProperties
        )
        profileRef.document(profile.id).set(profileData).await()
    }
}