package vn.edu.rmit.ui.screen.user.reserve

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.Booking
import vn.edu.rmit.data.model.Profile
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.BookingService
import javax.inject.Inject

data class ReserveScreenState(
    val profile: Profile = Profile(),
    val booking: List<Booking> = emptyList()
)

@HiltViewModel
class ReserveScreenViewModel @Inject constructor(
    private val accountService: AccountService,
    private val bookingService: BookingService,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReserveScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        getProfile()
        getBooking()
    }


    private fun getProfile() {
        viewModelScope.launch {
            accountService.getProfile()?.let { profile ->
                _uiState.update { state ->
                    state.copy(profile = profile)
                }
            }
        }
    }

    private fun getBooking() {
        viewModelScope.launch {
            val profile = accountService.getProfile()
            profile?.let {
                val booking = bookingService.findBookings(userId = profile.id)

                _uiState.update { state ->
                    state.copy(booking = booking)
                }

            }
        }
    }
}