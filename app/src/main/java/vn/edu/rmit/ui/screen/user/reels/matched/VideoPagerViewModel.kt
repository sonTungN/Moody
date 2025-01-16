package vn.edu.rmit.ui.screen.user.reels.matched

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.Video
import vn.edu.rmit.data.model.type.Mood
import vn.edu.rmit.data.service.MoodService
import vn.edu.rmit.data.service.VideoService
import javax.inject.Inject

data class VideoPagerState(
    val videos: List<Video> = emptyList(),
    val moods: List<Mood> = emptyList()
)

@HiltViewModel
class VideoPagerViewModel @Inject constructor(
    private val videoService: VideoService,
    private val moodService: MoodService
) : ViewModel() {
    private val _uiState = MutableStateFlow(VideoPagerState())
    val uiState = _uiState.asStateFlow()

    private val exoPlayerCache = mutableMapOf<String, ExoPlayer>()

    init {
        getMoods()
    }

    private fun getMoods() {
        viewModelScope.launch {
            moodService.getMoodsFlow().collect { moods ->
                _uiState.update { state ->
                    state.copy(moods = moods)
                }
            }
        }
    }

    fun loadVideosForMoods(selectedMoods: List<Mood>) {
        viewModelScope.launch {
            videoService
                .getVideos(selectedMoods.map { it.id })
                .collect { videos ->
                    _uiState.update { state ->
                        state.copy(videos = videos)
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayerCache.values.forEach { it.release() }
        exoPlayerCache.clear()
    }
}