package vn.edu.rmit.data.service

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import vn.edu.rmit.data.model.Role

interface RoleService {
    fun documentToRole(document: DocumentSnapshot): Role
    suspend fun getRoles(): List<Role>
    fun getRolesFlow(): Flow<List<Role>>
}