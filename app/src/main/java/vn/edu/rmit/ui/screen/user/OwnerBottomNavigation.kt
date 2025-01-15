package vn.edu.rmit.ui.screen.user

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import vn.edu.rmit.HomeRoute
import vn.edu.rmit.OwnerPropertyRoute
import vn.edu.rmit.R

@Composable
fun OwnerBottomNavigation(
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
                    Icons.Filled.AddLocation,
                    contentDescription = stringResource(R.string.add)
                )
            },
            label = { Text(stringResource(R.string.add)) },
            selected = false,
            onClick = {}

//            selected = currentScreen == HistoryRoute::class.qualifiedName,
//            onClick = { navigate(HistoryRoute) }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Hotel,
                    contentDescription = stringResource(R.string.properties)
                )
            },
            label = { Text(stringResource(R.string.properties)) },
            selected = currentScreen == OwnerPropertyRoute::class.qualifiedName,
            onClick = { navigate(OwnerPropertyRoute) }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Book,
                    contentDescription = stringResource(R.string.booking)
                )
            },
            label = { Text(stringResource(R.string.booking)) },
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

@Preview(showBackground = true)
@Composable
fun OwnerBottomNavigationPreview() {
    OwnerBottomNavigation(
        currentScreen = HomeRoute::class.qualifiedName,
        navigate = { route ->

        }
    )

}