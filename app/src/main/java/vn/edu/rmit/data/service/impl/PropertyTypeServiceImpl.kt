package vn.edu.rmit.data.service.impl

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import vn.edu.rmit.data.model.type.PropertyType
import vn.edu.rmit.data.service.PropertyTypeService
import javax.inject.Inject

class PropertyTypeServiceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : PropertyTypeService {
    override fun documentToPropertyType(document: DocumentSnapshot): PropertyType {
        return PropertyType(
            id = document.id,
            name = document.getString("name") ?: "",
        )
    }

    override fun getPropertyTypesFlow(): Flow<List<PropertyType>> {
        return db.collection("property_type").snapshots().map { snapshot ->
            snapshot.documents.map { documentToPropertyType(it) }
        }
    }

    override suspend fun getPropertyTypes(): List<PropertyType> {
        return db.collection("property_type").get().await().documents.map {
            documentToPropertyType(it)
        }
    }
}