package vn.edu.rmit.ui.screen.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.edu.rmit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTopBar(
    notificationCount: Long,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(R.drawable.health_metrics), "",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(stringResource(R.string.app_name), style = MaterialTheme.typography.titleLarge)
            }
        },
        actions = {
            IconButton(
                onClick = onNotificationClick
            ) {
                Icon(
                    if (notificationCount > 0) Icons.Default.Notifications else Icons.Default.NotificationsNone,
                    stringResource(R.string.notifications)
                )
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
fun PreviewUserTopBar() {
    UserTopBar(notificationCount = 10, onNotificationClick = {})
}