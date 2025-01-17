package vn.edu.rmit.ui.component.property

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FindInPage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import vn.edu.rmit.R
import vn.edu.rmit.data.model.Booking
import vn.edu.rmit.ui.component.button.ActionButton


@Composable
fun BookingReservation(
    booking: Booking,
    onShowDetails: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth(1f),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = booking.property.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = booking.property.address
                    )
                    Text(
                        text = "${booking.startDate} - ${booking.endDate}"
                    )
                }
                ActionButton(
                    onClick = onShowDetails,
                    icon = Icons.Filled.FindInPage,
                    text = stringResource(R.string.details),
                    contentDescription = stringResource(R.string.details),
                    modifier = Modifier
                )
            }

            Image(
                painter = rememberAsyncImagePainter(booking.property.image),
                contentDescription = "room image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(150.dp)
            )
        }
    }
}
