package vn.edu.rmit.ui.component.property

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BeachAccess
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HotelClass
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import vn.edu.rmit.data.model.Property

@Composable
fun PropertyItem(
    property: Property,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(true) { onClick() },
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val imageVector: ImageVector = when(property.type.id) {
                "Hotel" -> Icons.Default.HotelClass
                "Resort" -> Icons.Default.BeachAccess
                else -> Icons.Default.Home
            }
            Icon(
                imageVector,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(property.name, style = MaterialTheme.typography.titleMedium)
                Text(property.address, style = MaterialTheme.typography.bodyMedium)
                Text(property.type.name, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}