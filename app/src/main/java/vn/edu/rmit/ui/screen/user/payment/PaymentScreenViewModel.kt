package vn.edu.rmit.ui.screen.user.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.service.MoodService
import vn.edu.rmit.data.service.PropertyService
import javax.inject.Inject

data class PaymentScreenState(
    val property: Property = Property()
)

@HiltViewModel
class PaymentScreenViewModel @Inject constructor(
    state: SavedStateHandle,
    private val propertyService: PropertyService

    ) : ViewModel() {
    val id = state.get<String>("id")

    private val _uiState = MutableStateFlow(PaymentScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        getMoods()
    }

    private fun getMoods() {
        viewModelScope.launch {
            if (id != null) {
                propertyService.getProperty(id).let { property ->
                    _uiState.update { state ->
                        state.copy(property = property)
                    }
                }
            }
        }
    }
}