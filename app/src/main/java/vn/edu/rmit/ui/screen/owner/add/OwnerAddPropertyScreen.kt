package vn.edu.rmit.ui.screen.owner.add

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.ui.screen.owner.form.AddPropertyForm

@Composable
fun OwnerAddPropertyScreen(
    onCreate: () -> Unit,
    viewModel: OwnerAddPropertyScreenViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState
    val dataState by viewModel.dataState.collectAsState()

    AddPropertyForm(
        initialData = uiState.property,
        propertyTypes = dataState.propertyTypes,
        onSubmit = { property -> viewModel.create(property, onCreate) },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    )
}


