package vn.edu.rmit.ui.screen.user.settings

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

data class SettingScreenState(
    val profile: Profile = Profile()
)

@HiltViewModel
class SettingScreenViewModel
@Inject
constructor(private val accountService: AccountService) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        getProfile()
    }

    fun onLogoutClick(onSuccess: () -> Unit) {
        accountService.logout(onSuccess)
    }

    fun getProfile() {
        viewModelScope.launch {
            val profile = accountService.getProfile()

            if (profile !== null) {
                _uiState.update { currentState -> currentState.copy(profile = profile) }
            }
        }
    }
}
