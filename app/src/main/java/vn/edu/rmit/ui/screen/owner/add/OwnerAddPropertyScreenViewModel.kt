package vn.edu.rmit.ui.screen.owner.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.Profile
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.type.PropertyType
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.PropertyService
import vn.edu.rmit.data.service.PropertyTypeService
import javax.inject.Inject

data class PropertyCreateScreenState(
    val property: Property = Property(),
)

data class PropertyCreateDataState(
    val propertyTypes: List<PropertyType> = emptyList()
)

@HiltViewModel
class OwnerAddPropertyScreenViewModel
@Inject
constructor(
    private val propertyService: PropertyService,
    private val propertyTypeService: PropertyTypeService,
    private val accountService: AccountService
) : ViewModel() {
    var uiState by mutableStateOf(PropertyCreateScreenState())

    private val _dataState = MutableStateFlow(PropertyCreateDataState())
    val dataState = _dataState.asStateFlow()

    init {
        getPropertyTypes()
    }

    private fun getPropertyTypes() {
        viewModelScope.launch {
            val propertyTypes = propertyTypeService.getPropertyTypes()
            _dataState.update { state ->
                state.copy(propertyTypes = propertyTypes)
            }
        }
    }

    fun create(property: Property, onCreate: () -> Unit) {
        viewModelScope.launch {
            val currentUser = accountService.getProfile()

            val propertyToAdd = property.copy(
                owner = currentUser ?: Profile(),
                type = property.type,
                moodTags = property.moodTags
            )
            propertyService.addProperty(propertyToAdd)
            onCreate()
        }
    }
}
