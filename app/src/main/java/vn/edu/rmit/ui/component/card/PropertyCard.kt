package vn.edu.rmit.ui.component.card

import android.icu.text.MeasureFormat
import android.icu.util.Measure
import android.icu.util.MeasureUnit
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.HotelClass
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.edu.rmit.ui.theme.AppTypography
import java.time.LocalTime
import java.util.Locale

@Composable
fun PropertyCard(
    type: String,
    name: String,
    address: String,
//    bloodTypes: List<String>? = null,
    distance: Measure? = null,
    openingHours: LocalTime? = null,
    closingHours: LocalTime? = null,
    onClick: () -> Unit = {},
    onNavigationClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val icon = if (type == "Hotel") Icons.Filled.HotelClass else Icons.Filled.House
    Card(modifier = modifier, onClick = onClick) {
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(icon, contentDescription = "")
                Text(name, style = AppTypography.titleMedium)
                Text(address, style = AppTypography.bodySmall)
//                if (bloodTypes !== null && bloodTypes.isNotEmpty())
//                    Text(bloodTypes.joinToString(" | "), style = AppTypography.bodySmall)
                if (openingHours !== null && closingHours !== null)
                    Text("$openingHours - $closingHours", style = AppTypography.labelMedium)
            }
            if (distance !== null)
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(
                            Alignment.TopEnd
                        ),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FilledIconButton(onClick = onNavigationClick, modifier = Modifier.size(32.dp)) {
                        Icon(
                            Icons.Default.Directions,
                            contentDescription = "",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Text(
                        MeasureFormat.getInstance(
                            Locale.getDefault(),
                            MeasureFormat.FormatWidth.SHORT
                        )
                            .format(
                                distance
                            ).toString(),
                        style = AppTypography.bodySmall
                    )
                }
        }
    }
}

@Preview
@Composable
fun LocationCardPreview() {
    PropertyCard(
        type = "Hospital",
        name = "Tu Du Hospital",
        address = "284 Đ. Cống Quỳnh, Phường Phạm Ngũ Lão, Quận 1, Hồ Chí Minh 700000",
//        bloodTypes = listOf("A+", "AB+", "O-"),
        openingHours = LocalTime.of(8, 0),
        closingHours = LocalTime.of(20, 0),
        distance = Measure(9.1234, MeasureUnit.KILOMETER)
    )
}