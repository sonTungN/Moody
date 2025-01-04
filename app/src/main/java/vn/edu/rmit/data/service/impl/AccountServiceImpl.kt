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
import vn.edu.rmit.data.model.Role
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.RoleService
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val roleService: RoleService
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
            role = document
                .getDocumentReference("role")
                ?.snapshots()
                ?.map {
                    roleService.documentToRole(it)
                }?.first() ?: Role(),
        )
    }

    override fun authenticate(
        email: String, password: String, onSuccess: (role: String) -> Unit
    ) {
        auth
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.let {
                    db
                        .collection("profiles")
                        .document(it.uid)
                        .get()
                        .addOnSuccessListener {
                            currentUserRole = it.getDocumentReference("role")?.id
                            onSuccess(it.getDocumentReference("role")?.id ?: "unknown")
                        }
                } ?: onSuccess("unknown")
        }
    }

    override suspend fun register(
        email: String, password: String, profile: Profile, onSuccess: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).await()
        createProfile(currentUserId!!, profile)
        onSuccess()
    }

    override fun logout(onSuccess: () -> Unit) {
        currentUserRole = null
        auth.signOut()
        onSuccess()
    }

    override suspend fun getProfile(): Profile? = auth.currentUser?.let {
        profileRef.document(it.uid).get().await().let { documentToProfile(it) }
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
                "role" to profile.role.let { db.collection("roles").document(it.id) }
            )
        )
        return id
    }

    override suspend fun getProfileCount(): Long {
        return db.collection("profiles").count().get(AggregateSource.SERVER).await().count
    }
}