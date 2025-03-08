package vn.edu.rmit.ui.screen.manager.booking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.Booking
import vn.edu.rmit.data.model.Profile
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.service.BookingService
import javax.inject.Inject

data class ManagerBookingState(
    val booking: Booking = Booking(
        property = Property(),
        user = Profile()
    )
)

@HiltViewModel
class ManagerBookingViewModel @Inject constructor(
    state: SavedStateHandle,
    val bookingService: BookingService
) : ViewModel() {
    val id = state.get<String>("id")

    private val _uiState = MutableStateFlow(ManagerBookingState())
    val uiState = _uiState.asStateFlow()

    init {
        getBooking()
    }

    fun getBooking() {
        viewModelScope.launch {
            id?.let {
                bookingService.getBookingById(id).let { booking ->
                    _uiState.update { state ->
                        state.copy(booking = booking)
                    }
                }
            }
        }
    }
}