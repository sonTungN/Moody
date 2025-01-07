package vn.edu.rmit.ui.screen.user.booking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoorFront
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import vn.edu.rmit.R
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.ui.component.button.ActionButton
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
    var selectedRooms by remember { mutableStateOf(1) }
    var showDateModal by remember { mutableStateOf(false) }
    var showRoomModal by remember { mutableStateOf(false) }

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
            IconButton(
                onClick = onClose,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
            Text(
                text = "Room Details",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
//            RoomDetails(property) {
//
//            }

            val dateRangeText = if (selectedDateRange != null) {
                val (startDate, endDate) = selectedDateRange!!
                val formattedStartDate = startDate?.let {
                    SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(it))
                } ?: "Start"
                val formattedEndDate = endDate?.let {
                    SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(it))
                } ?: "End"
                "$formattedStartDate - $formattedEndDate"
            } else {
                stringResource(R.string.choose_date_range)
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActionButton(
                    onClick = { showDateModal = true },
                    icon = Icons.Filled.CalendarToday,
                    text = dateRangeText,
                    contentDescription = "choose date range",
                    modifier = Modifier
                )
                ActionButton(
                    onClick = { showRoomModal = true },
                    icon = Icons.Filled.Chair,
                    text = stringResource(R.string.select_room_amount) + " " + selectedRooms.toString(),
                    contentDescription = "select room amount",
                    modifier = Modifier
                )
            }
            if (showDateModal) {
                DateRangePickerModal(
                    onDateRangeSelected = {
                        selectedDateRange = it
                        showDateModal = false
                    },
                    onDismiss = { showDateModal = false }
                )
            }
            if (showRoomModal) {
                RoomPickerModal(
                    initialRooms = selectedRooms,
                    onDismiss = { showRoomModal = false },
                    onConfirm = { rooms ->
                        selectedRooms = rooms
                        showRoomModal = false
                    }
                )
            }
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

@Composable
fun RoomPickerModal(
    initialRooms: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var rooms by remember { mutableStateOf(initialRooms) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Number of Rooms") },
        text = {
            RoomAmountPicker(
                initValue = rooms,
                onValueChange = { rooms = it }
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(rooms) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun RoomAmountPicker(
    initValue: Int = 0,
    minValue: Int = 0,
    maxValue: Int = 10,
    onValueChange: (Int) -> Unit
) {
    var value by remember { mutableStateOf(initValue) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(48.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.White, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                if (value > minValue) {
                    value--
                    onValueChange(value)
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease",
                tint = Color.Blue
            )
        }

        Text(
            text = "$value",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        IconButton(
            onClick = {
                if (value < maxValue) {
                    value++
                    onValueChange(value)
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase",
                tint = Color.Blue
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomPickerModalPreview() {
    RoomPickerModal(
        1,
        onDismiss = {},
        onConfirm = {},
    )
}

@Preview(showBackground = true)
@Composable
fun BookingDetailsScreenPreview() {
    BookingDetailsScreen(onClose = {})
}
