package vn.edu.rmit.data.service

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import vn.edu.rmit.data.model.Video

interface VideoService {
    suspend fun documentToVideo(document: DocumentSnapshot): Video
    suspend fun getVideoById(id: String): Video
    fun getVideoByPropertyId(propertyId: String): Flow<List<Video>>
    fun getVideos(): Flow<List<Video>>
}