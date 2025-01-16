package vn.edu.rmit.ui.component.property

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import vn.edu.rmit.R
import vn.edu.rmit.data.model.Profile
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.type.Mood
import vn.edu.rmit.ui.component.button.ActionButton


@Composable
fun RoomDetails(
    property: Property,
    profile: Profile
) {
    val isSaved = remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth(1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box {
                    Column {
                        Text(
                            text = property.name
                        )
                        Text(
                            text = property.address
                        )
                    }
                }
                if (profile.savedProperties.contains(property.id)) {
                    ActionButton(
                        onClick = {
                            isSaved.value = !isSaved.value
                            if (isSaved.value) {
                                profile.savedProperties + property.id
                            }
                        },
                        icon = if (isSaved.value) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        text = if (isSaved.value) stringResource(R.string.saved) else stringResource(R.string.save),
                        contentDescription = if (isSaved.value) stringResource(R.string.saved) else stringResource(R.string.save),
                        modifier = Modifier
                    )

                } else {
                    ActionButton(
                        onClick = {
                            isSaved.value = !isSaved.value
                            if (!isSaved.value) {
                                profile.savedProperties - property.id
                            }
                        },
                        icon = if (isSaved.value) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        text = if (isSaved.value) stringResource(R.string.saved) else stringResource(R.string.save),
                        contentDescription = if (isSaved.value) stringResource(R.string.saved) else stringResource(R.string.save),
                        modifier = Modifier
                    )
                }

            }

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
        ),
        Profile()
    )
}