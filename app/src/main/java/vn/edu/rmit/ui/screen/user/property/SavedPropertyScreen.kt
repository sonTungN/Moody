package vn.edu.rmit.ui.screen.user.property

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.ui.component.property.PropertyItem

@Composable
fun SavedPropertyScreen(
//    id: String,
    viewModel: SavedPropertyScreenViewModel = hiltViewModel(),
    onPropertyClick: (id: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(uiState.properties) { property ->
            PropertyItem(
                property,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onPropertyClick(property.id) }
            )
        }
    }
}