package vn.edu.rmit.ui.screen.user.booking
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.R
import vn.edu.rmit.data.model.Profile
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.type.Mood
import vn.edu.rmit.ui.component.button.ActionButton
import vn.edu.rmit.ui.component.property.RoomDetails
import vn.edu.rmit.ui.component.select.DateRangePickerModal
import vn.edu.rmit.ui.component.select.RoomPickerModal
import vn.edu.rmit.ui.component.select.getFormattedDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    id: String,
    onReservedClick: (startDate: String, endDate: String, amount: String) -> Unit,
    viewModel: BookingScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    var selectedDateRange by remember { mutableStateOf<Pair<Long?, Long?>?>(null) }
    var selectedRoomAmount by remember { mutableStateOf(1) }
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
            Text(
                text = stringResource(R.string.booking_details),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            RoomDetails(uiState.property, uiState.profile)

            val dateRangeText = if (selectedDateRange != null) {
                val (startDate, endDate) = selectedDateRange!!
                val formattedStartDate = startDate?.let {
                    getFormattedDate(it)
                } ?: "Start"
                val formattedEndDate = endDate?.let {
                    getFormattedDate(it)
                } ?: "End"
                "$formattedStartDate - $formattedEndDate"
            } else {
                stringResource(R.string.choose_date_range)
            }

            Row(
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
                    text = stringResource(R.string.select_room_amount) + " " + selectedRoomAmount.toString(),
                    contentDescription = "select room amount",
                    modifier = Modifier
                )
            }
            Button(
                onClick = {
                    val startDate = selectedDateRange?.first?.let { getFormattedDate(it) } ?: ""
                    val endDate = selectedDateRange?.second?.let { getFormattedDate(it) } ?: ""
                    onReservedClick(startDate, endDate, selectedRoomAmount.toString())
                    viewModel.reserveProperty(uiState.property.id)
              },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.reserve))
            }
            if (showDateModal) {
                DateRangePickerModal(
                    onDateRangeSelected = {
                        selectedDateRange = it
                        showDateModal = false
                    },
//                    dateFormatter = DatePickerDefaults.dateFormatter("yy MM dd"),
                    onDismiss = { showDateModal = false }
                )
            }
            if (showRoomModal) {
                RoomPickerModal(
                    initialRooms = selectedRoomAmount,
                    onDismiss = { showRoomModal = false },
                    onConfirm = { rooms ->
                        selectedRoomAmount = rooms
                        showRoomModal = false
                    }
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookingScreenPreview() {
    RoomDetails(
        property = Property(
            name = "King Bedroom",
            address = "Some address, some ward, some district, some city, some country",
            moodTags = listOf(
                Mood(name = "Happy"),
                Mood(name = "Sad"),
            )
        ), Profile()
    )
}
