package vn.edu.rmit.data.service

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import vn.edu.rmit.data.model.Profile

interface AccountService {
    val currentUserId: String?
    val authenticated: Boolean

    suspend fun documentToProfile(document: DocumentSnapshot): Profile

    fun authenticate(email: String, password: String, onSuccess: (role: String) -> Unit)
    suspend fun register(email: String, password: String, profile: Profile, onSuccess: () -> Unit)
    fun logout(onSuccess: () -> Unit)

    suspend fun getProfile(): Profile?
    suspend fun getProfileById(id: String): Profile
    fun getProfileFlow(): Flow<List<Profile>>
    suspend fun createProfile(id: String, profile: Profile): String

    suspend fun getProfileCount(): Long
}