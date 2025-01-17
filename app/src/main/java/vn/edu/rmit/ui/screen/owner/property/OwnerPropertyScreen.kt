package vn.edu.rmit.ui.screen.owner.property

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.MapsHomeWork
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.R
import vn.edu.rmit.ui.component.empty.EmptyStateScreenWithCta
import vn.edu.rmit.ui.component.property.PropertyItem

@Composable
fun OwnerPropertyScreen(
    onAddNewPropertyClick: () -> Unit,
    onPropertyClick: (id: String) -> Unit,
    viewModel: OwnerPropertyScreenViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.properties.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.properties) { property ->
                PropertyItem(
                    property = property,
                    onClick = { onPropertyClick(property.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            EmptyStateScreenWithCta(
                icon = Icons.Default.MapsHomeWork,
                title = stringResource(R.string.no_property_title),
                description = stringResource(R.string.no_property_description),
                onActionClick = onAddNewPropertyClick,
                actionIcon = Icons.Default.AddHome,
                actionText = stringResource(R.string.add_property)
            )
        }
    }
}