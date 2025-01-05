package vn.edu.rmit.data.service

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import vn.edu.rmit.data.model.Property

interface PropertyService {
    suspend fun documentToProperty(document: DocumentSnapshot): Property
    suspend fun getProperty(id: String): Property
    fun getProperties(): Flow<List<Property>>
}