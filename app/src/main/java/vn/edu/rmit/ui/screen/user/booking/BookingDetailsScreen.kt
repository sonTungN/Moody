package vn.edu.rmit.ui.screen.user.booking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import vn.edu.rmit.R
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.type.PropertyType
import vn.edu.rmit.ui.component.button.ActionButton
import vn.edu.rmit.ui.component.video.VideoActionButtons
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BookingDetailsScreen(
//    property: Property,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDateRange by remember { mutableStateOf<Pair<Long?, Long?>?> (null) }
    var showModal by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Room Details",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
//            RoomDetails(property) {
//
//            }

            if (selectedDateRange != null) {
                val (startDate, endDate) = selectedDateRange!!
                val formattedStartDate = startDate?.let {
                    SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it))
                } ?: "No start date"
                val formattedEndDate = endDate?.let {
                    SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(it))
                } ?: "No end date"
            } else {
                Text("No date selected")
            }

            DateRangePickerModal(
                onDateRangeSelected = {
                    selectedDateRange = it
                    showModal = false
                },
                onDismiss = { showModal = false }
            )
        }
    }
}

@Composable
fun RoomDetails(
    property: Property,
    onBookedClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth(1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Text(
                text = property.name
            )
        }
        Image(
            painter = rememberAsyncImagePainter(property.image),
            contentDescription = "room image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(150.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = "Select date range"
                )
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookingDetailsScreenPreview() {
    BookingDetailsScreen(onClose = {})
}
