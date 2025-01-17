package vn.edu.rmit.ui.screen.owner.property

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

data class OwnerPropertyState(
    val properties: List<Property> = emptyList()
)

@HiltViewModel
class OwnerPropertyScreenViewModel @Inject constructor(
    private val propertyService: PropertyService,
    private val accountService: AccountService
) : ViewModel() {
    private val _uiState = MutableStateFlow(OwnerPropertyState())
    val uiState = _uiState.asStateFlow()

    init {
        getOwnedProperties()
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