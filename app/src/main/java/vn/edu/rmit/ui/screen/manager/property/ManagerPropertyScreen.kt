package vn.edu.rmit.ui.screen.manager.property

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import vn.edu.rmit.data.model.Video
import vn.edu.rmit.ui.component.property.PropertyDetails

@Composable
fun ManagerPropertyScreen(
    id: String,
    viewModel: ManagerPropertyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val result = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        result.value = it
    }

    Column {
        AsyncImage(model = uiState.property.image, contentDescription = uiState.property.name)

        PropertyDetails(uiState.property, modifier = Modifier.padding(16.dp))

        TextButton(onClick = {
            launcher.launch(
                PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.VideoOnly)
            )
        }) {
            Text(text = "Select Video")
        }

        if (result.value != null)
            Button(onClick = {
                // TOOD: define video as form
                viewModel.upload(result.value!!, property = uiState.property, video = Video())
            }) {
                Text(text = "Upload video")
            }

    }
}