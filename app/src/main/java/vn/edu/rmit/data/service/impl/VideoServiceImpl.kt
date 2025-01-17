package vn.edu.rmit.data.service.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import vn.edu.rmit.data.model.Video
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.MoodService
import vn.edu.rmit.data.service.VideoService
import java.util.UUID
import javax.inject.Inject

class VideoServiceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val database: FirebaseDatabase,
    private val accountService: AccountService,
    private val moodService: MoodService,
) : VideoService {
    private val videoRef = db.collection("videos")

    override suspend fun documentToVideo(document: DocumentSnapshot): Video {
        val moodTags = document.get("mood_tags")?.let {
            (it as List<*>).filterIsInstance<DocumentReference>()
        } ?: emptyList()

        return Video(
            id = document.id,
            title = document.getString("title") ?: "",
            desc = document.getString("desc") ?: "",
            url = document.getString("url") ?: "",
            propertyId = document.getString("propertyId") ?: "",
            moodTags = moodTags.map { tag ->
                tag.snapshots().map {
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

    override fun getVideoByPropertyId(propertyId: String): Flow<List<Video>> {
        return videoRef
            .whereEqualTo("propertyId", propertyId)
            .snapshots()
            .map { snapshot ->
                snapshot.documents.map { documentToVideo(it) }
            }
    }

    override fun getVideos(moods: List<String>): Flow<List<Video>> {
        var query: Query = videoRef

        if (moods.isNotEmpty()) query = query.whereArrayContainsAny(
            "mood_tags",
            moods.map { db.collection("moods").document(it) })

        return query.snapshots().map { snapshot ->
            snapshot.documents.map { documentToVideo(it) }
        }
    }

    override suspend fun addVideo(video: Video): Video {
        val id = UUID.randomUUID()
        videoRef.document(id.toString()).set(video.let {
            hashMapOf(
                "desc" to it.desc,
                "mood_tags" to it.moodTags.map { db.collection("moods").document(it.id) },
                "propertyId" to it.propertyId,
                "title" to it.title,
                "url" to it.url
            )
        }).await()

        return documentToVideo(videoRef.document(id.toString()).get().await())
    }

    override fun getCurrentUserSavedVideos(): Flow<List<Video>> = callbackFlow {
        val currentUserId = accountService.currentUserId
        if (currentUserId == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val allVideos = mutableMapOf<String, Video>()

        val videoListener = videoRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            launch {
                snapshot?.documents?.forEach { doc ->
                    allVideos[doc.id] = documentToVideo(doc)
                }
            }
        }

        val savedRef = database.reference.child("video_reaction")
        val savedListener = savedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch {
                    val savedVideos = allVideos.values.filter { video ->
                        snapshot.child(video.id)
                            .child("saved")
                            .child(currentUserId)
                            .exists()
                    }
                    trySend(savedVideos)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        })

        awaitClose {
            savedRef.removeEventListener(savedListener)
            videoListener.remove()
        }
    }

}