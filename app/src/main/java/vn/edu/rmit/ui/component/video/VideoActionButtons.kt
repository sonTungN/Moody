package vn.edu.rmit.ui.component.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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

        FilledTonalButton(
            onClick = onBookClick,
            modifier = Modifier.weight(1f),
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = stringResource(R.string.view_details),
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(R.string.view_details))
        }

        Button(
            onClick = onViewDetailClick,
            modifier = Modifier.weight(1f),
        ) {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = stringResource(R.string.book),
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(R.string.book))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun VideoActionButtonsPreview() {
    VideoActionButtons(
        onBookClick = {},
        onViewDetailClick = {}
    )
}
