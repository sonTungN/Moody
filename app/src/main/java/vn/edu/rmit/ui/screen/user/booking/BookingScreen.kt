package vn.edu.rmit.ui.screen.user.booking

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.edu.rmit.R
import vn.edu.rmit.data.model.Property

@Composable
fun BookingScreen(
    property: Property,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AccommodationProfile()
        Column(modifier = Modifier.padding(16.dp)) {
            property.types.forEach { propertyType ->
                RoomItem(
                    imageRes = propertyType.image,
                    roomTitle = propertyType.name
                )
            }
        }
    }
}

@Composable
fun RoomItem(imageRes: Int, roomTitle: String) {
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = roomTitle
                )
                Button(
                    onClick = { }
                ) {
                    Text("Reserve")
                }
            }
        }
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "room image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(150.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookingScreenPreview() {
    RoomItem(imageRes = R.drawable.hyatt_regency_danang_resort_and_spa_p372_ocean_view_king_guestroom_16x9, roomTitle = "Ocean View King Guest Room")
}