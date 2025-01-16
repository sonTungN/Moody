package vn.edu.rmit.ui.screen.user.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.android.PolyUtil
import com.google.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.edu.rmit.data.model.type.PropertyType
import vn.edu.rmit.data.service.PropertyService
import vn.edu.rmit.data.service.PropertyTypeService
import javax.inject.Inject

data class PropertyState(
    val points: List<com.google.android.gms.maps.model.LatLng> = emptyList(),
    val propertyTypes: List<PropertyType> = emptyList(),
)

@HiltViewModel
class NearbyScreenViewModel
@Inject
constructor(
    private val propertyService: PropertyService,
    private val propertyTypeService: PropertyTypeService,
    private val geoApiContext: GeoApiContext
) : ViewModel() {
    private val _uiState = MutableStateFlow(PropertyState())
    var uiState: StateFlow<PropertyState> = _uiState

    val properties = propertyService.getProperties()

    init {
        getPropertyTypes()
    }

    private fun getPropertyTypes() {
        viewModelScope.launch {
            propertyTypeService.getPropertyTypes().let { types ->
                _uiState.update { state ->
                    state.copy(propertyTypes = types)
                }
            }
        }
    }

    fun getDirection(origin: LatLng, destination: LatLng) {
        viewModelScope.launch {
            val result =
                DirectionsApi.newRequest(geoApiContext).origin(origin).destination(destination)
                    .await()
            val steps = result.routes.first().legs.first().steps

            _uiState.update { state ->
                state.copy(points = steps.flatMap { PolyUtil.decode(it.polyline.encodedPath) }
                )
            }
        }
    }
}
