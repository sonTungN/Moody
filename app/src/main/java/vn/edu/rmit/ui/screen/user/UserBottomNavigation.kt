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
import vn.edu.rmit.HomeRoute
import vn.edu.rmit.R
import vn.edu.rmit.ReservationRoute

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
            selected = currentScreen == HomeRoute::class.qualifiedName,
            onClick = { navigate(HomeRoute) }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search)
                )
            },
            label = { Text(stringResource(R.string.search)) },
            selected = false,
            onClick = {}

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
                    contentDescription = stringResource(R.string.save)
                )
            },
            label = { Text(stringResource(R.string.save)) },
            selected = false,
            onClick = {}

        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.settings)
                )
            },
            label = { Text(stringResource(R.string.settings)) },
            selected = false,
            onClick = {}
        )
    }
}