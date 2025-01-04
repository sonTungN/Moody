package vn.edu.rmit.ui.screen.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.edu.rmit.data.service.AccountService
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {
    fun onLoginClick(email: String, password: String, onSuccess: (role: String) -> Unit) {
        viewModelScope.launch {
            accountService.authenticate(email, password, onSuccess)
            Log.d("LoginScreenViewModel", onSuccess.toString())
        }
    }
}
