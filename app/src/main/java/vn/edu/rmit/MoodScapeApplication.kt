package vn.edu.rmit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import vn.edu.rmit.ui.screen.owner.OwnerBottomNavigation
import vn.edu.rmit.ui.screen.user.UserBottomNavigation
import vn.edu.rmit.ui.screen.user.UserTopBar

@Composable
fun MoodScapeApplication(
    viewModel: MoodScapeApplicationViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel.currentUser) {
        viewModel.getProfile()
    }

    val isVisibleBar = !(
            currentScreen?.startsWith(MoodFilterRoute::class.qualifiedName ?: "") == true ||
            currentScreen?.startsWith(SlideVideoPagerRoute::class.qualifiedName ?: "") == true
    )

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            if (viewModel.authenticated() && isVisibleBar)
                UserTopBar(
                    notificationCount = 0,
                    onNotificationClick = {},
                )
        },
        bottomBar = {
            if (viewModel.authenticated() && isVisibleBar) {
                if (uiState.profile.role.id == "traveler")
                    UserBottomNavigation(
                        currentScreen = currentScreen,
                        navigate = { screen ->
                            navController.popBackStack(screen, inclusive = true)
                            navController.navigate(screen)
                        }
                    )
                if (uiState.profile.role.id == "owner")
                    OwnerBottomNavigation(
                        currentScreen = currentScreen,
                        navigate = { screen ->
                            navController.popBackStack(screen, inclusive = true)
                            navController.navigate(screen)
                        }
                    )
            }
        }
    )
    { innerPadding ->
        MoodScapeRoutes(
            navController = navController,
            authenticated = viewModel.authenticated(),
            role = uiState.profile.role.id,
            modifier = Modifier.padding(innerPadding)
        )
    }
}