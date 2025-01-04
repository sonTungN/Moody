package vn.edu.rmit.ui.component.video

import android.util.Log
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoDetailViewModel @OptIn(UnstableApi::class)
@Inject constructor(
    val videoPlayer: ExoPlayer
) : ViewModel() {

    private var _uiState = MutableStateFlow<VideoDetailsIUState>(VideoDetailsIUState.Default)
    var uiState = _uiState.asStateFlow()

    init {
        videoPlayer.apply {
            repeatMode = REPEAT_MODE_ONE
            playWhenReady = true
            videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
        }
    }

    fun handleAction(action: VideoDetailAction) {
        when(action) {
            is VideoDetailAction.LoadData -> { loadVideo(action.videoUrl) }
            is VideoDetailAction.ToggleVideo -> { toggleVideo() }
        }
    }

    private fun loadVideo(videoUrl: String) {
        _uiState.value = VideoDetailsIUState.Loading
        viewModelScope.launch {
            try {
                delay(1000L)
                playVideo(videoUrl)
                _uiState.value = VideoDetailsIUState.Success

            } catch (e: Exception) {
                _uiState.value = VideoDetailsIUState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun toggleVideo() {
        if (videoPlayer.isPlaying) {
            videoPlayer.pause()
        } else {
            videoPlayer.play()
        }
    }

    @OptIn(UnstableApi::class)
    private fun playVideo(videoUrl: String) {
        val mediaItem = MediaItem.fromUri(videoUrl)
        videoPlayer.setMediaItem(mediaItem)
        videoPlayer.prepare()
        videoPlayer.play()
    }

    override fun onCleared() {
        super.onCleared()
        videoPlayer.release()
    }
}

sealed interface VideoDetailsIUState {
    data object Default : VideoDetailsIUState
    data object Loading: VideoDetailsIUState
    data object Success: VideoDetailsIUState
    data class Error(val message: String): VideoDetailsIUState
}

sealed class VideoDetailAction {
    data class LoadData(val videoUrl: String): VideoDetailAction()
    data object ToggleVideo: VideoDetailAction()
}