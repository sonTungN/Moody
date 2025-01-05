package vn.edu.rmit.ui.screen.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.Profile
import vn.edu.rmit.data.service.AccountService
import javax.inject.Inject

data class HomeScreenState(
    val profile: Profile = Profile(),
    val count: Long = 0,
    val selectedMoods: List<String> = emptyList()
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            val profile = accountService.getProfile()

            profile?.let {
                _uiState.update { currentState ->
                    currentState.copy(profile = it)
                }
            }
        }
    }

//    fun updateSelectedMoods(moods: List<String>) {
//        _uiState.update { currentState ->
//            currentState.copy(selectedMoods = moods)
//        }
//    }
}
