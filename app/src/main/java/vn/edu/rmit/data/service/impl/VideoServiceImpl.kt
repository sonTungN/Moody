package vn.edu.rmit.data.service.impl

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.Video
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.MoodService
import vn.edu.rmit.data.service.PropertyService
import vn.edu.rmit.data.service.VideoService
import javax.inject.Inject

class VideoServiceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    val accountService: AccountService,
) : VideoService {

    private val videoRef = db.collection("videos")

    override suspend fun documentToVideo(document: DocumentSnapshot): Video {
        return Video(
            id = document.id,
            title = document.getString("title") ?: "",
            desc = document.getString("desc") ?: "",
            url = document.getString("url") ?: "",
            propertyId = document.getString("propertyId") ?: "",
            moodTags = (document.get("mood_tags") as? List<*>)?.mapNotNull {
                it?.toString()
            } ?: emptyList()
        )
    }

    override suspend fun getVideoById(id: String): Video {
        return videoRef.document(id).get().await().let {
            documentToVideo(it)
        }
    }

    override fun getVideoByPropertyId(propertyId: String): Flow<List<Video>> {
        return videoRef
            .whereEqualTo("propertyId", propertyId)
            .snapshots()
            .map { snapshot ->
                snapshot.documents.map { documentToVideo(it) }
            }
    }

    override fun getVideos(): Flow<List<Video>> {
        return videoRef.snapshots().map { snapshot ->
            snapshot.documents.map { documentToVideo(it) }
        }
    }
}