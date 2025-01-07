package vn.edu.rmit.ui.component.select

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun RoomPickerModal(
    initialRooms: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var rooms by remember { mutableStateOf(initialRooms) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Number of Rooms") },
        text = {
            RoomAmountPicker(
                initValue = rooms,
                onValueChange = { rooms = it }
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(rooms) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun RoomAmountPicker(
    initValue: Int = 0,
    minValue: Int = 0,
    maxValue: Int = 10,
    onValueChange: (Int) -> Unit
) {
    var value by remember { mutableStateOf(initValue) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(48.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.White, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                if (value > minValue) {
                    value--
                    onValueChange(value)
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease",
                tint = Color.Blue
            )
        }

        Text(
            text = "$value",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        IconButton(
            onClick = {
                if (value < maxValue) {
                    value++
                    onValueChange(value)
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase",
                tint = Color.Blue
            )
        }
    }
}