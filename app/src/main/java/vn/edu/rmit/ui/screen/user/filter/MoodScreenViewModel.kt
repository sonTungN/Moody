package vn.edu.rmit.ui.screen.user.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.type.Mood
import vn.edu.rmit.data.service.MoodService
import javax.inject.Inject

data class MoodScreenState(
    val moods: List<Mood> = emptyList()
)

@HiltViewModel
class MoodScreenViewModel @Inject constructor(
    private val moodService: MoodService
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoodScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        getMoods()
    }

    private fun getMoods() {
        viewModelScope.launch {
            val moods = moodService.getMoods()
            moods.let { _uiState.value = MoodScreenState(moods = it) }
        }
    }
}