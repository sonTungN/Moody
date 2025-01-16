package vn.edu.rmit.ui.component.mood

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import vn.edu.rmit.data.model.type.Mood


fun getMoodIcon(mood: Mood): @Composable () -> Unit = {
    when (mood.id) {
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