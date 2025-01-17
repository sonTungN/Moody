package vn.edu.rmit.data.service

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import vn.edu.rmit.data.model.Video

interface VideoService {
    suspend fun documentToVideo(document: DocumentSnapshot): Video
    suspend fun getVideoById(id: String): Video
    suspend fun addVideo(video: Video): Video
    fun getVideoByPropertyId(propertyId: String): Flow<List<Video>>
    fun getVideos(moods: List<String> = emptyList()): Flow<List<Video>>
    fun getCurrentUserSavedVideos(): Flow<List<Video>>
}