package vn.edu.rmit.ui.component.property

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import vn.edu.rmit.R
import vn.edu.rmit.data.model.Property
import vn.edu.rmit.data.model.type.Mood


@Composable
fun RoomDetails(
    property: Property
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
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = property.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = property.address
                    )
                }

                TextButton(
                    onClick = {
                        isSaved.value = !isSaved.value
                    },
                ) {
                    if (isSaved.value)
                        Icon(
                            Icons.Filled.Bookmark,
                            stringResource(R.string.saved),
                            modifier = Modifier.size(
                                ButtonDefaults.IconSize
                            )
                        )
                    else
                        Icon(
                            Icons.Filled.BookmarkBorder,
                            stringResource(R.string.saved),
                            modifier = Modifier.size(
                                ButtonDefaults.IconSize
                            )
                        )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    if (isSaved.value)
                        Text(stringResource(R.string.saved))
                    else
                        Text(stringResource(R.string.save))

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
        )
    )
}