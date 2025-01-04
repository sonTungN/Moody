package vn.edu.rmit.ui.component.video

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun VideoInfo(
    accountName: String,
    videoName: String,
    description: String,
    moodTags: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
//            .fillMaxWidth()
//            .padding(16.dp)
    ) {
        Text(
            text = "@$accountName",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}