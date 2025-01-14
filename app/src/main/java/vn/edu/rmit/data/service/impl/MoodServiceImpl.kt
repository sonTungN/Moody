package vn.edu.rmit.data.service.impl

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import vn.edu.rmit.data.model.type.Mood
import vn.edu.rmit.data.service.MoodService
import javax.inject.Inject

class MoodServiceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : MoodService {
    private val locationRef = db.collection("moods")

    override fun documentToMood(document: DocumentSnapshot): Mood {
        return Mood(
            id = document.id,
            name = document.getString("name") ?: "",
        )
    }

    override suspend fun getMoods(): List<Mood> {
        return locationRef.get().await().documents.map {
            documentToMood(it)
        }
    }

    override fun getMoodsFlow(): Flow<List<Mood>> {
        return locationRef.snapshots().map { snapshot ->
            snapshot.documents.map { documentToMood(it) }
        }
    }

}