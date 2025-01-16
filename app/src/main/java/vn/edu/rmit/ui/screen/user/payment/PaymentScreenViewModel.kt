package vn.edu.rmit.ui.screen.user.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.Profile
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.MoodService
import vn.edu.rmit.data.service.PropertyService
import vn.edu.rmit.ui.screen.user.booking.BookingUiState
import javax.inject.Inject

data class PaymentScreenState(
    val property: Property = Property(),
    val profile: Profile = Profile()
)

@HiltViewModel
class PaymentScreenViewModel @Inject constructor(
    state: SavedStateHandle,
    private val propertyService: PropertyService,
    private val accountService: AccountService
): ViewModel() {
    val id = state.get<String>("id")

    private val _uiState = MutableStateFlow(PaymentScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        getBooking()
        getProfile()
    }

    private fun getBooking() {
        viewModelScope.launch {
            if (id !== null)
                propertyService.getProperty(id).let { property ->
                    _uiState.update { state ->
                        state.copy(property = property)
                    }
                }
        }
    }

    private fun getProfile() {
        if (id !== null)
            viewModelScope.launch {
                accountService.getProfile()?.let { profile ->
                    _uiState.update { state ->
                        state.copy(profile = profile)
                    }
                }
            }
    }

    fun reserveProperty(propertyId: String) {
        viewModelScope.launch {
            val userProfile = accountService.getProfile()
            val updatedBookings = (userProfile?.booking ?: emptyList()) + listOfNotNull(propertyId)

            accountService.getProfile()?.let {
                accountService.updateProfile(
                    it.copy(booking = updatedBookings)
                )
            }

            val property = propertyService.getProperty(propertyId)
            val updatedTravelers = (property.travelers + listOfNotNull(userProfile)).distinctBy { it.id }

            propertyService.updateProperty(
                property.copy(travelers = updatedTravelers)
            )
        }
    }
}