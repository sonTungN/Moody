package vn.edu.rmit.ui.component.video

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.service.VideoActionService
import javax.inject.Inject

@HiltViewModel
class VideoDetailViewModel @OptIn(UnstableApi::class)
@Inject constructor(
    val videoPlayer: ExoPlayer,
    private val videoActionService: VideoActionService,
    private val auth: FirebaseAuth
) : ViewModel() {

    private var _uiState = MutableStateFlow(VideoDetailUiState())
    var uiState = _uiState.asStateFlow()

    init {
        videoPlayer.apply {
            repeatMode = REPEAT_MODE_ONE
            playWhenReady = true
            videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
            volume = 1f
        }
    }

    fun observeVideoDetailActionStatus(videoId: String) {
        viewModelScope.launch {
            videoActionService.observeVideoReactions(videoId).collect {
                reactCount -> _uiState.update { it.copy(reactCount = reactCount) }
            }
        }

        viewModelScope.launch {
            auth.currentUser?.uid.let { id ->
                videoActionService.observeUserReactedVideo(videoId, id!!).collect {
                    recentReactStatus -> _uiState.update { it.copy(reactStatus = recentReactStatus) }
                }
            }
        }

        viewModelScope.launch {
            auth.currentUser?.uid.let { id ->
                videoActionService.observeUserSavedVideo(videoId, id!!).collect {
                    recentSaveStatus -> _uiState.update { it.copy(saveStatus = recentSaveStatus) }
                }
            }
        }
    }

    fun toggleReaction(videoId: String) {
        viewModelScope.launch {
            auth.currentUser?.uid.let { videoActionService.toggleReaction(videoId, it!!) }
        }
    }

    fun toggleSave(videoId: String) {
        viewModelScope.launch {
            auth.currentUser?.uid.let { videoActionService.toggleSave(videoId, it!!) }
        }
    }

    fun handleAction(action: VideoDetailAction) {
        when(action) {
            is VideoDetailAction.LoadData -> { loadVideo(action.videoUrl) }
            is VideoDetailAction.ToggleVideo -> { toggleVideo() }
        }
    }

    private fun loadVideo(videoUrl: String) {
        _uiState.update { it.copy(playerState = VideoPlayerState.Loading) }
        viewModelScope.launch {
            try {
                delay(1000L)
                playVideo(videoUrl)
                _uiState.update { it.copy(playerState = VideoPlayerState.Success) }

            } catch (e: Exception) {
                _uiState.update { it.copy(playerState = VideoPlayerState.Error(e.message!!)) }
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

data class VideoDetailUiState (
    val playerState: VideoPlayerState = VideoPlayerState.Default,
    val reactCount: Int = 0,
    val reactStatus: Boolean = false,
    val saveStatus: Boolean = false
)

sealed interface VideoPlayerState {
    data object Default : VideoPlayerState
    data object Loading: VideoPlayerState
    data object Success: VideoPlayerState
    data class Error(val message: String): VideoPlayerState
}

sealed class VideoDetailAction {
    data class LoadData(val videoUrl: String): VideoDetailAction()
    data object ToggleVideo: VideoDetailAction()
}