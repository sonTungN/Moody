package vn.edu.rmit.ui.screen.manager.property

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import vn.edu.rmit.R
import vn.edu.rmit.data.model.Video
import vn.edu.rmit.ui.component.property.PropertyDetails
import vn.edu.rmit.ui.screen.owner.form.AddPropertyForm

@Composable
fun ManagerPropertyScreen(
    onUpdate: () -> Unit,
    id: String,
    role: String,
    viewModel: ManagerPropertyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        result.value = it
    }

    var showEditDialog by remember { mutableStateOf(false)}

    if (showEditDialog) {
        Dialog(onDismissRequest = { showEditDialog = false }) {
            Card (
                modifier = Modifier.fillMaxWidth()
            ) {
                AddPropertyForm(
                    isMoodTextDisplayed = false,
                    initialData = uiState.property,
                    propertyTypes = uiState.propertyTypes,
                    onSubmit = { updatedProperty ->
                        viewModel.updateProperty(updatedProperty, onUpdate = onUpdate)
                        showEditDialog = !showEditDialog
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(model = uiState.property.image, contentDescription = uiState.property.name)

        PropertyDetails(
            property = uiState.property,
            modifier = Modifier.padding(16.dp)
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            if (role == "owner") {
                OutlinedButton(
                    onClick = { showEditDialog = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit),
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(R.string.edit))
                }
            }

            Button(
                onClick = {
                    launcher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.VideoOnly
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Default.VideoLibrary,
                    contentDescription = "Add Video"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Select Video")
            }

            if (result.value != null)
                Button(
                    onClick = {
                        viewModel.upload(
                            result.value!!,
                            property = uiState.property,
                            video = Video()
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.Upload,
                        contentDescription = "Upload Video"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Upload Video")
                }
        }
    }
}
