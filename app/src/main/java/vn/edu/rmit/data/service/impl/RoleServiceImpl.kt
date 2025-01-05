package vn.edu.rmit.data.service.impl

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import vn.edu.rmit.data.model.type.Role
import vn.edu.rmit.data.service.RoleService
import javax.inject.Inject

class RoleServiceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : RoleService {
    private val locationRef = db.collection("roles")

    override fun documentToRole(document: DocumentSnapshot): Role {
        return Role(
            id = document.id,
            name = document.getString("name") ?: "",
        )
    }

    override suspend fun getRoles(): List<Role> {
        return locationRef.get().await().documents.map {
            documentToRole(it)
        }
    }

    override fun getRolesFlow(): Flow<List<Role>> {
        return locationRef.snapshots().map { snapshot ->
            snapshot.documents.map { documentToRole(it) }
        }
    }

}