package vn.edu.rmit.ui.screen.owner.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import vn.edu.rmit.R
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.type.PropertyType
import vn.edu.rmit.ui.component.card.EmotionCard
import vn.edu.rmit.ui.component.select.TimePickerModal
import vn.edu.rmit.ui.screen.user.filter.Emotion
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddPropertyForm(
    isMoodTextDisplayed: Boolean = true,
    initialData: Property = Property(),
    propertyTypes: List<PropertyType> = emptyList(),
    onSubmit: (Property) -> Unit,
    viewModel: AddPropertyFormViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableStateOf(initialData) }
    var isTypeExpanded by remember { mutableStateOf(false) }
    var isOpeningHourOpen by remember { mutableStateOf(false) }
    var isClosingHourOpen by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()
    var selectedEmotions by remember { mutableStateOf(initialData.moodTags.map { it.id }.toSet()) }

    val scrollState = rememberScrollState()

    val emotions = uiState.moods.map { mood ->
        Emotion(
            id = mood.id,
            name = mood.name,
            icon = getMoodIcon(mood.id)
        )
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            initialData.geoPoint ?: LatLng(15.9028182, 105.80665705),
            if (initialData.geoPoint !== null) 15.0f else 5.0f
        )
    }

    if (isOpeningHourOpen) {
        TimePickerModal(
            initialTime = state.openingHours,
            onTimeSelected = { time ->
                state = state.copy(openingHours = time)
            },
            onDismiss = {
                isOpeningHourOpen = !isOpeningHourOpen
            }
        )
    }

    if (isClosingHourOpen) {
        TimePickerModal(
            initialTime = state.closingHours,
            onTimeSelected = { time ->
                state = state.copy(closingHours = time)
            },
            onDismiss = {
                isClosingHourOpen = !isClosingHourOpen
            }
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(
                scrollState,
                enabled = !cameraPositionState.isMoving
            )
            .padding(bottom = 8.dp)
    ) {
        OutlinedTextField(
            value = state.name,
            onValueChange = { state = state.copy(name = it) },
            label = { Text(stringResource(R.string.property_name)) },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = isTypeExpanded,
            onExpandedChange = {
                isTypeExpanded = !isTypeExpanded
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = state.type.name,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isTypeExpanded)
                },
                label = { Text(stringResource(R.string.property_type)) },
                modifier = Modifier
                    .menuAnchor(type = MenuAnchorType.PrimaryEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = isTypeExpanded,
                onDismissRequest = {
                    isTypeExpanded = !isTypeExpanded
                },
            ) {
                for (type in propertyTypes) {
                    DropdownMenuItem(
                        text = { Text(type.name) },
                        onClick = {
                            state = state.copy(type = type.copy())
                            isTypeExpanded = !isTypeExpanded
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.available_time))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { isOpeningHourOpen = !isOpeningHourOpen }
            ) {
                Text(state.openingHours.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)))
            }
            OutlinedButton(
                onClick = { isClosingHourOpen = !isClosingHourOpen }
            ) {
                Text(state.closingHours.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)))
            }
        }

        Text(stringResource(R.string.property_mood))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                userScrollEnabled = false
            ) {
                items(emotions) { emotion ->
                    EmotionCard(
                        isTextDisplay = isMoodTextDisplayed,
                        emotion = emotion,
                        isSelected = selectedEmotions.contains(emotion.id),
                        onSelect = {
                            selectedEmotions = if (selectedEmotions.contains(emotion.id)) {
                                selectedEmotions - emotion.id
                            } else {
                                selectedEmotions + emotion.id
                            }
                        }
                    )
                }
            }
        }

        Text(stringResource(R.string.address))
        OutlinedTextField(
            value = state.address,
            onValueChange = { state = state.copy(address = it) },
            label = { Text(stringResource(R.string.location)) },
            modifier = Modifier.fillMaxWidth()
        )

        Box {
            Icon(
                Icons.Default.Add,
                "",
                modifier = Modifier
                    .align(Alignment.Center)
                    .zIndex(1f),
                tint = MaterialTheme.colorScheme.primary
            )
            GoogleMap(
                cameraPositionState = cameraPositionState,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
        }

        Button(
            onClick = {
                onSubmit(
                    state.copy(
                        geoPoint = cameraPositionState.position.target,
                        moodTags = uiState.moods.filter { selectedEmotions.contains(it.id) }
                    )
                )
          },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(stringResource(R.string.submit))
        }
    }
}

@Composable
private fun getMoodIcon(moodId: String): @Composable () -> Unit = {
    when (moodId) {
        "happy" -> Icon(
            Icons.Filled.SentimentSatisfiedAlt,
            "Happy",
            modifier = Modifier.size(40.dp)
        )
        "sad" -> Icon(
            Icons.Filled.SentimentDissatisfied,
            "Sad",
            modifier = Modifier.size(40.dp)
        )
        "angry" -> Icon(
            Icons.Filled.LocalFireDepartment,
            "Angry",
            modifier = Modifier.size(40.dp)
        )
        "tired" -> Icon(
            Icons.Filled.BatteryAlert,
            "Tired",
            modifier = Modifier.size(40.dp)
        )
        "bored" -> Icon(
            Icons.Filled.SentimentNeutral,
            "Bored",
            modifier = Modifier.size(40.dp)
        )
        "adventurous" -> Icon(
            Icons.Filled.Explore,
            "Adventurous",
            modifier = Modifier.size(40.dp)
        )
        else -> Icon(
            Icons.Filled.SentimentNeutral,
            "Default",
            modifier = Modifier.size(40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LocationFormPreview() {
    AddPropertyForm(
        onSubmit = {},
    )
}