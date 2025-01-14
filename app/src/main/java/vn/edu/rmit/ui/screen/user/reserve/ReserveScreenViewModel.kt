package vn.edu.rmit.ui.screen.user.reserve

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
import vn.edu.rmit.data.service.PropertyService
import javax.inject.Inject

data class ReserveScreenState(
    val profile: Profile = Profile(),
    val properties: List<Property> = emptyList(),
)

@HiltViewModel
class ReserveScreenViewModel @Inject constructor(
    private val accountService: AccountService,
    private val propertyService: PropertyService,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReserveScreenState())
    val uiState = _uiState.asStateFlow()


    init {
        getProfile()
        getReservation()
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

    private fun getReservation() {
        viewModelScope.launch {
            val profile = accountService.getProfile()
            profile?.let { userProfile ->
                val properties = propertyService.getProperties().first()
                val userProperties = userProfile.booking.mapNotNull { bookingId ->
                    properties.find { property -> property.id == bookingId }
                }
                _uiState.update { state ->
                    state.copy(properties = userProperties)
                }
            }
        }
    }
}
