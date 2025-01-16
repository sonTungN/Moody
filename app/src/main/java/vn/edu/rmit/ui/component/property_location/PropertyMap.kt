package vn.edu.rmit.ui.component.property_location

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HotelClass
import androidx.compose.material.icons.filled.House
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import vn.edu.rmit.R
import vn.edu.rmit.data.model.Property

@Composable
fun PropertyMap(
    enableMyCollection: Boolean,
    properties: List<Property>,
    points: List<LatLng>,
    focusedPosition: LatLng?,
    innerOffset: PaddingValues,
    onPropertyClick: (Property) -> Unit,
    modifier: Modifier = Modifier
) {
    val mapPropertiesState by remember {
        derivedStateOf {
            MapProperties(
                isMyLocationEnabled = enableMyCollection
            )
        }
    }

    val mapUiSettingsState by remember {
        derivedStateOf {
            MapUiSettings(
                myLocationButtonEnabled = enableMyCollection
            )
        }
    }

    val cameraPositionState = rememberCameraPositionState() {
        position = CameraPosition.fromLatLngZoom(LatLng(15.9028182, 105.80665705), 5.0f)
    }

    LaunchedEffect(key1 = properties) {
        if (properties.isNotEmpty()) {
            val bounds = LatLngBounds.builder().apply {
                properties.forEach {
                    Log.d("GEOPOINT", it.name)
                    Log.d("GEOPOINT", it.geoPoint.toString())
                    if (it.geoPoint !== null) include(it.geoPoint)
                }
            }.build()
            cameraPositionState.move(
                update = CameraUpdateFactory.newLatLngBounds(bounds, 100)
            )
        }
    }

    LaunchedEffect(focusedPosition) {
        if (focusedPosition !== null)
            cameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                        focusedPosition,
                        cameraPositionState.position.zoom
                    )
                )
            )
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        contentPadding = innerOffset,
        properties = mapPropertiesState,
        uiSettings = mapUiSettingsState,
        modifier = modifier,
    ) {
        properties.filter { it.geoPoint != null }.map { property ->
            val icon = resourceToBitmap(
                LocalContext.current,
//                val icon = if (type == "Hotel") Icons.Filled.HotelClass else Icons.Filled.House
                if (property.type.name == "Hotel") R.drawable.hotel else R.drawable.resort
            )

            Marker(
                state = rememberMarkerState(position = property.geoPoint!!),
                title = property.name,
                onInfoWindowClick = { onPropertyClick(property) },
                icon = icon
            )
        }
        if (points.isNotEmpty()) {
            Polyline(
                points = points
            )
        }
    }
}

private fun resourceToBitmap(context: Context, vectorResId: Int): BitmapDescriptor? {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
    vectorDrawable?.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

    val bitmap = vectorDrawable?.let {
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        vectorDrawable.draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    return bitmap;
}