package vn.edu.rmit.ui.screen.user.property

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import vn.edu.rmit.R
import vn.edu.rmit.ui.component.property.PropertyDetails

@Composable
fun PropertyScreen(
    id: String,
    onBookingClick: (id: String) -> Unit,
    viewModel: PropertyScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        AsyncImage(model = uiState.property.image, contentDescription = uiState.property.name)

        PropertyDetails(uiState.property, modifier = Modifier.padding(16.dp))

        Button(
            onClick = { onBookingClick(id) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                Icons.Default.Hotel, stringResource(R.string.reserve), modifier = Modifier.size(
                    ButtonDefaults.IconSize
                )
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(R.string.reserve))
        }
    }
}