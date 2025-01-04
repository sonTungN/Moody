package vn.edu.rmit.ui.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StatisticCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(),
    long: Boolean = false,
) {
    Card(
        modifier = modifier.aspectRatio(if (!long) 1f else 2f),
        colors = colors,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                title,
                style = MaterialTheme.typography.displayLarge,
            )
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
fun StatisticCardPreview() {
    StatisticCard(
        title = "10",
        subtitle = "Donations",
        long = true
    )
}