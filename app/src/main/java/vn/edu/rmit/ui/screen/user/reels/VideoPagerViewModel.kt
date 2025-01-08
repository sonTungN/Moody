package vn.edu.rmit.ui.screen.user.reels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.Video
import vn.edu.rmit.data.service.VideoService
import javax.inject.Inject

@HiltViewModel
class VideoPagerViewModel @Inject constructor(
    private val videoService: VideoService,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos = _videos.asStateFlow()

    private val exoPlayerCache = mutableMapOf<String, ExoPlayer>()

    fun loadVideosForMoods(selectedMoods: List<String>) {
        viewModelScope.launch {
            videoService
                .getVideos().map { videos ->
                    videos.filter { video ->
                        video.moodTags.any { mood ->
                            selectedMoods.contains(mood)
                        }
                    }
                }
                .collect { filteredVideos ->
                    _videos.value = filteredVideos
                }
        }
    }

    fun loadSavedVideo() {
        viewModelScope.launch {
            videoService
                .getCurrentUserSavedVideos()
                .collect{ savedVideos ->
                    _videos.value = savedVideos
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayerCache.values.forEach { it.release() }
        exoPlayerCache.clear()
    }
}