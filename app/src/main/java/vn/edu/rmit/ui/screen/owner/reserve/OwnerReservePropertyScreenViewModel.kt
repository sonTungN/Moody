package vn.edu.rmit.ui.screen.owner.reserve

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.PropertyService
import javax.inject.Inject

data class OwnerReservePropertyState(
    val properties: List<Property> = emptyList()
)

@HiltViewModel
class OwnerReservePropertyScreenViewModel @Inject constructor(
    private val propertyService: PropertyService,
    private val accountService: AccountService
) : ViewModel() {
    private val _uiState = MutableStateFlow(OwnerReservePropertyState())
    val uiState = _uiState.asStateFlow()

    init {
        getReservedProperties()
    }

    private fun getReservedProperties() {
        viewModelScope.launch {
            val ownerProfile = accountService.getProfile() // Get the current owner's profile
            ownerProfile?.let { owner ->
                val allProfiles = accountService.getProfileFlow().first() // Get all traveler profiles
                val allProperties = propertyService.getProperties().first() // Get all properties

                // Filter properties owned by the current owner
                val ownerProperties = allProperties.filter { property ->
                    owner.ownedProperties.contains(property.id)
                }

                // Loop through all travelers' bookings
                val reservedProperties = mutableListOf<Property>()
                allProfiles.forEach { traveler ->
                    traveler.booking.forEach { bookedPropertyId ->
                        ownerProperties.find { it.id == bookedPropertyId }?.let { property ->
                            reservedProperties.add(property)
                        }
                    }
                }

                // Update UI state with reserved properties
                _uiState.update { state ->
                    state.copy(properties = reservedProperties.distinct()) // Remove duplicates if any
                }
            }
        }
    }

    private fun getOwnedProperties() {
        viewModelScope.launch {
            val profile = accountService.getProfile()
            profile?.let { userProfile ->
                val properties = propertyService.getProperties().first()

                val ownerProperties = properties.filter { property ->
                    userProfile.ownedProperties.contains(property.id)
                }

                _uiState.update { state ->
                    state.copy(properties = ownerProperties)
                }
            }
        }
    }
}