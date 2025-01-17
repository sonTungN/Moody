package vn.edu.rmit.ui.screen.manager

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import vn.edu.rmit.ManagerPropertiesRoute
import vn.edu.rmit.R
import vn.edu.rmit.SettingsRoute

@Composable
fun ManagerBottomNavigation(
    currentScreen: String?,
    navigate: (Any) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Hotel,
                    contentDescription = stringResource(R.string.properties)
                )
            },
            label = { Text(stringResource(R.string.properties)) },
            selected = currentScreen == ManagerPropertiesRoute::class.qualifiedName,
            onClick = { navigate(ManagerPropertiesRoute) }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.settings)
                )
            },
            label = { Text(stringResource(R.string.settings)) },
            selected = currentScreen == SettingsRoute::class.qualifiedName,
            onClick = { navigate(SettingsRoute) }
        )
    }
}