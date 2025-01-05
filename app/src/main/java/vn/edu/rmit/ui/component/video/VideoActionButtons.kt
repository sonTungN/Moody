package vn.edu.rmit.ui.component.video

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.edu.rmit.R
import vn.edu.rmit.ui.component.button.ActionButton

@Composable
fun VideoActionButtons(
    onBookClick: () -> Unit,
    onViewDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        ActionButton(
            onClick = onBookClick,
            icon = Icons.Filled.CalendarToday,
            text = stringResource(R.string.book),
            contentDescription = "book",
            modifier = Modifier.weight(1f)
        )

        ActionButton(
            onClick = onViewDetailClick,
            icon = Icons.Filled.Info,
            text = stringResource(R.string.view_details),
            contentDescription = "details",
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = false,)
@Composable
fun VideoActionButtonsPreview() {
    VideoActionButtons(
        onBookClick = {},
        onViewDetailClick = {}
    )
}
