package vn.edu.rmit.ui.screen.user.location

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.type.PropertyType
import vn.edu.rmit.ui.component.dialog.LocationGrantDialog
import vn.edu.rmit.ui.component.property_location.PropertyList
import vn.edu.rmit.ui.component.property_location.PropertyMap
import java.time.LocalTime

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(
    onPropertyClick: (property: Property) -> Unit,
    viewModel: NearbyScreenViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    var search by remember { mutableStateOf("") }
    var timeValue by remember { mutableStateOf<LocalTime?>(null) }
    var propertyType by remember { mutableStateOf<PropertyType?>(null) }

    val uiState by viewModel.uiState.collectAsState()
    val properties by viewModel.properties.collectAsStateWithLifecycle(emptyList())
    val searchProperties by remember {
        derivedStateOf {
            properties
                .filter { property ->
                    property.name.lowercase().contains(search.lowercase().trim())
                }
                .filter { property -> if (propertyType !== null) property.type.id == propertyType?.id else true }
                .filter { property ->
                    if (timeValue !== null)
                        property.openingHours.isBefore(timeValue) &&
                        property.closingHours.isAfter(timeValue)

                    else true
                }
        }
    }
    var focusedPosition by remember { mutableStateOf<LatLng?>(null) }

    val locationPermissionState = rememberPermissionState (
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    var showPermissionDialog by remember { mutableStateOf(!locationPermissionState.status.isGranted) }

    val context = LocalContext.current
    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    var currentLocation: Location? by remember { mutableStateOf(null) }

    LaunchedEffect(locationPermissionState.status.isGranted) {
        if (locationPermissionState.status.isGranted) {
            val result = locationClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                CancellationTokenSource().token,
            ).await()
            result?.let { currentLocation = it }
        }
    }

    if (showPermissionDialog)
        LocationGrantDialog (
            onDismissRequest = { showPermissionDialog = !showPermissionDialog },
            onRequest = {
                locationPermissionState.launchPermissionRequest()
                showPermissionDialog = !showPermissionDialog
            }
        )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 256.dp,
        sheetContent = {
            PropertyList (
                modifier = Modifier.fillMaxHeight(0.9f),
                properties = searchProperties,
                searchValue = search,
                onSearchChange = { search = it },
                onPropertyClick = onPropertyClick,
                onNavigationClick = { location ->
                    location.geoPoint?.let { focusedPosition = it }
                    scope.launch(Dispatchers.IO) { scaffoldState.bottomSheetState.partialExpand() }

                    // TEMPORARILY DISABLED since Google Maps Platform requires active billing
                    /*
                    if (currentLocation !== null && location.geoPoint !== null) {
                        viewModel.getDirection(
                            com.google.maps.model.LatLng(
                                currentLocation!!.latitude,
                                currentLocation!!.longitude
                            ),
                            com.google.maps.model.LatLng(
                                location.geoPoint.latitude,
                                location.geoPoint.longitude
                            )
                        )
                    }
                     */
                },
                currentLocation = currentLocation,

                timeValue = timeValue,
                onTimeValueChange = { timeValue = it },

//                bloodTypes = uiState.bloodTypes,
//                bloodTypeValue = bloodType,
//                onBloodTypeChange = { bloodType = it },

                propertyTypes = uiState.propertyTypes,
                propertyTypeValue = propertyType,
                onPropertyTypeChange = { propertyType = it },

                onClear = {
                    timeValue = null
//                    bloodType = null
                    propertyType = null
                }
            )
        }
    ) { innerOffset ->
        PropertyMap(
            enableMyCollection = locationPermissionState.status.isGranted,
            properties = properties,
            points = uiState.points,
            focusedPosition = focusedPosition,
            innerOffset = innerOffset,
            modifier = Modifier.fillMaxWidth(),
            onPropertyClick = onPropertyClick
        )
    }
}
