package vn.edu.rmit.ui.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoodTagCard(
    content: String,
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(),
) {
    Card(
        modifier = modifier,
        colors = colors,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .wrapContentWidth(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                text = "#$content",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
fun MoodTagCardPreview() {
    MoodTagCard(
        content = "Adventurous"
    )
}