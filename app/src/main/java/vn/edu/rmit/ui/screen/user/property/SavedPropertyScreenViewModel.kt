package vn.edu.rmit.ui.screen.user.property

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

data class SavedPropertyState(
    val properties: List<Property> = emptyList()
)

@HiltViewModel
class SavedPropertyScreenViewModel @Inject constructor(
    private val propertyService: PropertyService,
    private val accountService: AccountService
) : ViewModel() {
    private val _uiState = MutableStateFlow(SavedPropertyState())
    val uiState = _uiState.asStateFlow()

    init {
        getSavedProperties()
    }

    private fun getSavedProperties() {
        viewModelScope.launch {
            val profiles =
                accountService.getProfileFlow().first() // Get all traveler profiles
            val properties = propertyService.getProperties().first() // Get all properties

            val savedProperties = mutableListOf<Property>()
            profiles.forEach { traveler ->
                traveler.savedProperties.forEach { savedPropertyId ->
                    properties.find { it.id == savedPropertyId }?.let { property ->
                        savedProperties.add(property)
                    }
                }
            }

            _uiState.update { state ->
                state.copy(properties = savedProperties.distinct()) // Remove duplicates if any
            }
        }
    }
}