package vn.edu.rmit.ui.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
    isTextDisplay: Boolean = true,
    emotion: Emotion,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .scale(if (isSelected) 1f else 0.95f)
            .clickable(onClick = onSelect)
    ) {
        Card(
            modifier = Modifier
                .matchParentSize(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(if (isSelected) 2.dp else 0.dp, Color.Black),
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
                if (isTextDisplay) {
                    Text(
                        text = emotion.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }

        if (isSelected) {
            Surface(
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-6).dp, y = (6).dp),
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                border = BorderStroke(1.5.dp, Color.Black),
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "checked",
                    tint = Color(0xFF07A06E),
                    modifier = Modifier.padding(1.dp)
                )
            }
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