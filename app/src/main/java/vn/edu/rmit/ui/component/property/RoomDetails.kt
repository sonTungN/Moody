package vn.edu.rmit.ui.component.property

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.type.Mood


@Composable
fun RoomDetails(
    property: Property
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth(1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text(
                text = property.name
            )
            Text(
                text = property.address

            )
            Image(
                painter = rememberAsyncImagePainter(property.image),
                contentDescription = "room image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(150.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomDetailsPreview() {
    RoomDetails(
        property = Property(
            name = "King Bedroom",
            address = "Some address, some ward, some district, some city, some country",
            moodTags = listOf(
                Mood(name = "Happy"),
                Mood(name = "Sad"),
            )
        )
    )
}