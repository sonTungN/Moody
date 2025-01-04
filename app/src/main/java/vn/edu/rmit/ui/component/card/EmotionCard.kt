package vn.edu.rmit.ui.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.edu.rmit.ui.screen.user.filter.Emotion

@Composable
fun EmotionCard(
    modifier: Modifier = Modifier,
    emotion: Emotion,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(8.dp)
            .aspectRatio(1f)
            .scale(if (isSelected) 1.1f else 1f)
            .clickable(onClick = onSelect),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Color.Black),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            emotion.icon()
            Text(
                text = emotion.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmotionCardPreview() {
    EmotionCard(
        emotion = Emotion("happy", "Happy") {
            Icon(
                Icons.Filled.SentimentSatisfiedAlt,
                "Happy",
                modifier = Modifier.size(40.dp)
            )
        },
        isSelected = true,
        onSelect = { }
    )
}