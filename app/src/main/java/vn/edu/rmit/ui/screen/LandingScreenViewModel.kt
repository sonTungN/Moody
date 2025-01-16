package vn.edu.rmit.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import vn.edu.rmit.data.service.AccountService
import javax.inject.Inject

@HiltViewModel
class LandingScreenViewModel
@Inject
constructor(
    private val accountService: AccountService
) : ViewModel() {
    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            accountService.logout(onSuccess)
        }
    }
}