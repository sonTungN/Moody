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
import vn.edu.rmit.ui.component.select.DateRangePickerModal
import vn.edu.rmit.ui.component.select.RoomAmountPicker
import vn.edu.rmit.ui.component.select.RoomPickerModal
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
