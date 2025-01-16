package vn.edu.rmit.ui.component.property

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.type.Mood
import vn.edu.rmit.ui.component.MoodTagSection

@Composable
fun PropertyDetails(
    property: Property,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(property.name, style = MaterialTheme.typography.titleLarge)
        Text(property.address, style = MaterialTheme.typography.bodySmall)
        MoodTagSection(property.moodTags.map { it.name })
    }
}

@Preview(showBackground = true)
@Composable
fun PropertyDetailsPreview() {
    PropertyDetails(
        property = Property(
            name = "Property 1",
            address = "Some address, some ward, some district, some city, some country",
            moodTags = listOf(
                Mood(name = "Happy"),
                Mood(name = "Sad"),
            )
        ),
        modifier = Modifier.fillMaxWidth()
    )
}