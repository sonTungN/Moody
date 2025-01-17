package vn.edu.rmit.ui.screen.manager.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.Booking
import vn.edu.rmit.data.service.BookingService
import javax.inject.Inject

data class ManagerBookingState(
    val bookings: List<Booking> = emptyList()
)

@HiltViewModel
class ManagerBookingsViewModel @Inject constructor(
    private val bookingService: BookingService
) : ViewModel() {
    private val _uiState = MutableStateFlow(ManagerBookingState())
    val uiState = _uiState.asStateFlow()

    init {
        getBookings()
    }

    fun getBookings() {
        viewModelScope.launch {
            bookingService.findBookings().let { bookings ->
                _uiState.update { state ->
                    state.copy(bookings = bookings)
                }
            }
        }
    }
}