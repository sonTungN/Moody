package vn.edu.rmit.ui.screen.manager.bookings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.ui.component.booking.BookingDetails

@Composable
fun ManagerBookingsScreen(
    viewModel: ManagerBookingsViewModel = hiltViewModel(),
    onBookingClick: (id: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn {
        items(uiState.bookings, { it.id }) { item ->
            BookingDetails(
                item,
                onClick = { onBookingClick(item.id) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}