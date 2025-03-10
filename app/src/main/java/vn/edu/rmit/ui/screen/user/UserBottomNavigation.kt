package vn.edu.rmit.ui.screen.user

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import vn.edu.rmit.MoodFilterRoute
import vn.edu.rmit.PropertyLocationRoute
import vn.edu.rmit.R
import vn.edu.rmit.ReservationRoute
import vn.edu.rmit.SavePropertyRoute
import vn.edu.rmit.SettingsRoute

@Composable
fun UserBottomNavigation(
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
                    Icons.Filled.Home,
                    contentDescription = stringResource(R.string.home)
                )
            },
            label = { Text(stringResource(R.string.home)) },
            selected = currentScreen == MoodFilterRoute::class.qualifiedName,
            onClick = { navigate(MoodFilterRoute) }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search)
                )
            },
            label = { Text(stringResource(R.string.search)) },
            selected = currentScreen == PropertyLocationRoute::class.qualifiedName,
            onClick = { navigate(PropertyLocationRoute) }

//            selected = currentScreen == HistoryRoute::class.qualifiedName,
//            onClick = { navigate(HistoryRoute) }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Hotel,
                    contentDescription = stringResource(R.string.reserved)
                )
            },
            label = { Text(stringResource(R.string.reserved)) },
            selected = currentScreen == ReservationRoute::class.qualifiedName,
            onClick = { navigate(ReservationRoute) }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Bookmark,
                    contentDescription = stringResource(R.string.saved)
                )
            },
            label = { Text(stringResource(R.string.saved)) },
            selected = currentScreen == SavePropertyRoute::class.qualifiedName,
            onClick = { navigate(SavePropertyRoute) }

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