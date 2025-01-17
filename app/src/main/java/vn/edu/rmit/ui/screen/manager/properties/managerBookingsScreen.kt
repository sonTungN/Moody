package vn.edu.rmit.ui.screen.manager.properties

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import vn.edu.rmit.ui.component.property.PropertyItem

@Composable
fun ManagerPropertiesScreen(
    viewModel: ManagerPropertiesViewModel = hiltViewModel(),
    onPropertyClick: (id: String) -> Unit,
) {
    val properties by viewModel.properties.collectAsStateWithLifecycle(emptyList())

    LazyColumn {
        items(properties, { it.id }) { item ->
            PropertyItem(
                property = item,
                onClick = { onPropertyClick(item.id) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}