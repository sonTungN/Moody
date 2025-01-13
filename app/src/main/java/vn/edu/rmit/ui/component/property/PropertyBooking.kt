package vn.edu.rmit.ui.component.property

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.type.Mood

@Composable
fun PropertyBooking(
    property: Property,
    modifier: Modifier = Modifier
) {
}

@Preview(showBackground = true)
@Composable
fun PropertyBookingPreview(
) {
    PropertyBooking(property = Property(
        name = "Property 1",
        address = "Some address, some ward, some district, some city, some country",
        moodTags = listOf(
            Mood(name = "Happy"),
            Mood(name = "Sad"),
        )
    ),
        modifier = Modifier.fillMaxWidth())
}