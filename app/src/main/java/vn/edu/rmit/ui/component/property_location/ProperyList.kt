package vn.edu.rmit.ui.component.property_location

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import android.location.Location
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import vn.edu.rmit.R
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.type.PropertyType
import vn.edu.rmit.ui.component.card.PropertyCard
import vn.edu.rmit.ui.component.select.PropertyTypeSelect
import vn.edu.rmit.ui.component.select.TimePickerModal
import java.time.LocalTime

@Composable
fun PropertyList(
    properties: List<Property>,
    searchValue: String,
    onSearchChange: (String) -> Unit,
    onPropertyClick: (Property) -> Unit,
    onNavigationClick: (Property) -> Unit,
    currentLocation: Location?,
    timeValue: LocalTime? = null,
    onTimeValueChange: ((LocalTime) -> Unit)? = null,
    propertyTypes: List<PropertyType>? = null,
    propertyTypeValue: PropertyType? = null,
    onPropertyTypeChange: ((PropertyType) -> Unit)? = null,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showTimePicker by remember { mutableStateOf(false) }

    if (showTimePicker && onTimeValueChange !== null)
        TimePickerModal (
            initialTime = timeValue ?: LocalTime.of(0, 0),
            onTimeSelected = onTimeValueChange,
            onDismiss = { showTimePicker = !showTimePicker }
        )

    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = searchValue,
            onValueChange = { onSearchChange(it) },
            label = { Text(stringResource(R.string.search)) },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = timeValue !== null,
                onClick = { showTimePicker = !showTimePicker },
                leadingIcon = {
                    Icon(
                        Icons.Default.AccessTime,
                        stringResource(R.string.time),
                        modifier = Modifier.size(AssistChipDefaults.IconSize)
                    )
                },
                label = { Text(timeValue?.toString() ?: stringResource(R.string.available_time)) }
            )

            if (propertyTypes !== null && onPropertyTypeChange !== null)
                PropertyTypeSelect(
                    propertyTypes = propertyTypes,
                    propertyTypeValue = propertyTypeValue,
                    onPropertyTypeChange = { onPropertyTypeChange(it) },
                )
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(properties, { it.id }) { location ->
                val distance = if (currentLocation !== null && location.geoPoint !== null)
                    SphericalUtil.computeDistanceBetween(
                        LatLng(
                            currentLocation.latitude,
                            currentLocation.longitude
                        ), location.geoPoint
                    )
                else null

                PropertyCard (
                    type = location.type.name,
                    name = location.name,
                    address = location.address,
                    distance = if (distance !== null) Measure(
                        distance / 1000,
                        MeasureUnit.KILOMETER
                    ) else null,
                    openingHours = location.openingHours,
                    closingHours = location.closingHours,
//                    bloodTypes = location.bloodType.map { it.name },
                    onClick = { onPropertyClick(location) },
                    onNavigationClick = { onNavigationClick(location) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun NearbyListPreview() {
    PropertyList(
        properties = listOf(
            Property(name = "test"), Property(name = "test")
        ),
        searchValue = "",
        onSearchChange = {},
        onPropertyClick = {},
        onNavigationClick = {},
        currentLocation = null,
        onClear = {}
    )
}