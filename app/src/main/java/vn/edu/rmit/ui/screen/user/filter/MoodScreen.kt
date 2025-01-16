package vn.edu.rmit.ui.screen.user.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import vn.edu.rmit.R
import vn.edu.rmit.ui.component.card.EmotionCard

data class Emotion(
    val id: String,
    val name: String,
    val icon: @Composable () -> Unit
)

@Composable
fun MoodScreen(
    viewModel: MoodScreenViewModel = hiltViewModel(),
    onSelected: (List<String>) -> Unit,
    onSkip: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    var selectedEmotions by remember { mutableStateOf<Set<String>>(emptySet()) }

    val emotions = uiState.moods.map { mood ->
        Emotion(
            id = mood.id,
            name = mood.name,
            icon = getMoodIcon(mood.id)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                stringResource(R.string.mood_form_description),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )

            LazyVerticalGrid(
                modifier = Modifier.padding(16.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(emotions) { emotion ->
                    EmotionCard(
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

        Column(
            modifier = Modifier.padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (selectedEmotions.isNotEmpty()) {
                Text(
                    text = if (selectedEmotions.size > 1) "Selected: ${selectedEmotions.size} moods" else "Selected: 1 mood",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Button(
                onClick = { onSelected(selectedEmotions.toList()) },
                enabled = selectedEmotions.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text(
                    text = stringResource(R.string._continue),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onSkip,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
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
fun MoodFormPreview() {
    MoodScreen(onSkip = {}, onSelected = {})
}