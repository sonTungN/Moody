package vn.edu.rmit.data.service.impl

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import vn.edu.rmit.data.model.Video
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.MoodService
import vn.edu.rmit.data.service.VideoService
import javax.inject.Inject

class VideoServiceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    val accountService: AccountService,
    private val moodService: MoodService,
) : VideoService {

    private val videoRef = db.collection("videos")

    override suspend fun documentToVideo(document: DocumentSnapshot): Video {
        val moods =
            document.get("moodTags")?.let {
                (it as List<*>).filterIsInstance<DocumentReference>()
            } ?: emptyList()

        return Video(
            title = document.getString("title") ?: "",
            desc = document.getString("desc") ?: "",
            url = document.getString("url") ?: "",
            moodTags = moods.map { it ->
                it.snapshots().map {
                    moodService.documentToMood(it)
                }.first()
            }.toList()
        )
    }

    override suspend fun getVideoById(id: String): Video {
        return videoRef.document(id).get().await().let {
            documentToVideo(it)
        }
    }

    override fun getVideos(): Flow<List<Video>> {
        return videoRef.snapshots().map { snapshot ->
            snapshot.documents.map { documentToVideo(it) }
        }
    }
}