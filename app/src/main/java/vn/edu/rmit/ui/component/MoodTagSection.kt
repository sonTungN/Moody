package vn.edu.rmit.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.edu.rmit.ui.component.card.MoodTagCard

@Composable
fun MoodTagSection(
    moodTags: List<String>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        moodTags.forEach { mood ->
            MoodTagCard(content = mood)
        }
    }
}

@Preview (showBackground = false)
@Composable
fun MoodTagSectionPreview() {
    MoodTagSection(
        moodTags = listOf("Happy", "Sad")
    )
}