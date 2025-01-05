package vn.edu.rmit.data.service

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import vn.edu.rmit.data.model.type.PropertyType

interface PropertyTypeService {
    fun documentToLocationType(document: DocumentSnapshot): PropertyType
    fun getPropertyTypesFlow(): Flow<List<PropertyType>>
    suspend fun getPropertyTypes(): List<PropertyType>
}