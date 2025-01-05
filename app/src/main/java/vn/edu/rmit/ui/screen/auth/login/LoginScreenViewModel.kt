package vn.edu.rmit.ui.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.service.AccountService
import javax.inject.Inject

data class LoginScreenState(
    val error: String? = null
)

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginScreenState())
    val uiState = _uiState.asStateFlow()

    fun onLoginClick(
        email: String,
        password: String,
        onSuccess: (role: String) -> Unit,
    ) : Result<Unit> {
        viewModelScope.launch {
            try {
                accountService
                    .authenticate(email, password, onSuccess)
                    .onSuccess {
                        _uiState.update { state ->
                            state.copy("Authentication success")
                        }
                    }
                    .onFailure { e->
                        _uiState.update { state ->
                            state.copy(error = e.message ?: "Authentication failed")
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(error = e.message ?: "Authentication failed")
                }
            }
        }

        return Result.success(Unit)
    }

    fun resetErrorState() {
        _uiState.update { state -> state.copy(error = null) }
    }
}
