package vn.edu.rmit.ui.screen.owner.reserve

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.R
import vn.edu.rmit.ui.component.empty.EmptyStateScreen
import vn.edu.rmit.ui.component.property.PropertyReservation

@Composable
fun OwnerReservedPropertyScreen(
//    id: String,
    viewModel: OwnerReservePropertyScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

//    val properties = uiState.properties.map { property ->
//        Property(
//            id = property.id,
//            name = property.name,
//            image = property.image,
//            address = property.address
//        )
//    }

    if (uiState.properties.isEmpty()) {
        EmptyStateScreen(
            icon = Icons.Default.BookmarkRemove,
            title = stringResource(R.string.no_booking),
            description = stringResource(R.string.no_booking_description)
        )
    } else {
        LazyColumn(
            modifier = modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.properties) { property ->
                PropertyReservation(
                    property,
                    onShowDetails = {}
                )
            }
        }
    }
}