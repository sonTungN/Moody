package vn.edu.rmit.ui.screen.user.property

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import vn.edu.rmit.ui.component.property.PropertyDetails

@Composable
fun PropertyScreen(id: String, viewModel: PropertyScreenViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        AsyncImage(model = uiState.property.image, contentDescription = uiState.property.name)

        PropertyDetails(uiState.property, modifier = Modifier.padding(16.dp))
    }
}