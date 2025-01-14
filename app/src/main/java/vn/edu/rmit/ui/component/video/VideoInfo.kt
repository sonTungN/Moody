package vn.edu.rmit.ui.component.video

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.edu.rmit.ui.component.MoodTagSection

@Composable
fun VideoInfo(
    author: String,
    videoTitle: String,
    description: String,
    moodTags: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = videoTitle,
            style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = description,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        MoodTagSection(
            moodTags = moodTags,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Preview (showBackground = false)
@Composable
fun VideoInfoPreview() {
    VideoInfo(
        author = "Son Tung",
        videoTitle = "Test Title",
        description = "This is the test description. This is the test description.",
        moodTags = listOf("Happy", "Sad")
    )
}