package vn.edu.rmit.data.service

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import vn.edu.rmit.data.model.type.Mood

interface MoodService {
    fun documentToMood(document: DocumentSnapshot): Mood
    suspend fun getMoods(): List<Mood>
    fun getMoodsFlow(): Flow<List<Mood>>
}