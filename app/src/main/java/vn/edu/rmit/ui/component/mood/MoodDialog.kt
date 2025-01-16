package vn.edu.rmit.ui.component.mood

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.edu.rmit.data.model.type.Mood
import vn.edu.rmit.ui.component.card.EmotionCard
import vn.edu.rmit.ui.screen.user.filter.Emotion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodDialog(
    selected: Set<Mood>,
    moods: List<Mood>,
    onDismiss: () -> Unit,
    onConfirm: (Set<Mood>) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedEmotions by remember {
        mutableStateOf(selected.toSet())
    }

    BasicAlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Select mood", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.size(12.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(moods) {
                        EmotionCard(
                            emotion = Emotion(
                                id = it.id,
                                name = it.name,
                                icon = getMoodIcon(it)
                            ),
                            isSelected = selectedEmotions.contains(it),
                            onSelect = {
                                selectedEmotions = if (selectedEmotions.contains(it)) {
                                    selectedEmotions - it
                                } else {
                                    selectedEmotions + it
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                TextButton(
                    onClick = { onConfirm(selectedEmotions) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Confirm")
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MoodDialogPreview() {
    MoodDialog(
        selected = emptySet(),
        moods = listOf(Mood(id = "", name = "Happy"), Mood(id = "", name = "Sad")),
        onConfirm = {},
        onDismiss = {}
    )
}