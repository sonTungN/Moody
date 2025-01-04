package vn.edu.rmit.ui.screen.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.Profile
import vn.edu.rmit.data.model.Role
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.RoleService
import javax.inject.Inject

data class RegisterScreenState(
    val roles: List<Role> = emptyList(),
)

@HiltViewModel
class RegisterScreenViewModel
@Inject
constructor(
    private val accountService: AccountService,
    private val roleService: RoleService,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        getRoles()
    }

    private fun getRoles() {
        viewModelScope.launch {
            roleService.getRoles().let { roles ->
                _uiState.update { state ->
                    state.copy(roles = roles)
                }
            }
        }
    }

    fun onRegisterClick(
        name: String,
        role: Role,
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            accountService.register(
                email, password, Profile(
                    fullName = name,
                    role = role,
                ), onSuccess
            )
        }
    }
}
