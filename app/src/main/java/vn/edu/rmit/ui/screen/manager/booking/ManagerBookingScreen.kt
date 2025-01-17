package vn.edu.rmit.ui.screen.manager.booking

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.ui.component.ProfileDetails
import vn.edu.rmit.ui.component.property.PropertyDetails

@Composable
fun ManagerBookingScreen(
    id: String,
    viewModel: ManagerBookingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        PropertyDetails(
            property = uiState.booking.property,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Text(
            "${uiState.booking.startDate} - ${uiState.booking.endDate}",
            modifier = Modifier.padding(16.dp)
        )

        ProfileDetails(
            name = uiState.booking.user.fullName,
            role = uiState.booking.user.role.name,
            modifier = Modifier.fillMaxWidth()
        )
    }
}