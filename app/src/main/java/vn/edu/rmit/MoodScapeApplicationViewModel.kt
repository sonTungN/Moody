package vn.edu.rmit

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

data class MoodScapeApplicationState(
    val profile: Profile = Profile(),
)

@HiltViewModel
class MoodScapeApplicationViewModel @Inject constructor(
    private val accountService: AccountService,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MoodScapeApplicationState())
    val uiState = _uiState.asStateFlow()

    val currentUser
        get() = accountService.currentUserId

    fun authenticated() = accountService.authenticated

    init {
        getProfile()
    }

    fun getProfile() {
        viewModelScope.launch {
            accountService.getProfile().let { profile ->
                _uiState.update { state ->
                    state.copy(profile = profile ?: Profile())
                }
            }
        }
    }
}