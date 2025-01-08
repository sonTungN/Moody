package vn.edu.rmit.data.service

import kotlinx.coroutines.flow.Flow

interface VideoActionService {
    fun observeVideoReactions(videoId: String): Flow<Int>
    fun observeUserReactedVideo(videoId: String, userId: String): Flow<Boolean>
    suspend fun toggleReaction(videoId: String, userId: String)

    fun observeUserSavedVideo(videoId: String, userId: String): Flow<Boolean>
    suspend fun toggleSave(videoId: String, userId: String)

//    fun cleanUpDtbListener()
}