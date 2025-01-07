package vn.edu.rmit

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import vn.edu.rmit.ui.component.video.VideoActionBar
import vn.edu.rmit.ui.screen.LandingScreen
import vn.edu.rmit.ui.screen.auth.login.LoginScreen
import vn.edu.rmit.ui.screen.auth.register.RegisterScreen
import vn.edu.rmit.ui.screen.user.booking.BookingScreen
import vn.edu.rmit.ui.screen.user.filter.MoodScreen
import vn.edu.rmit.ui.screen.user.home.HomeScreen
import vn.edu.rmit.ui.screen.user.reels.VideoPagerScreen

@Serializable
object AuthenticationRoute

@Serializable
object LoginRoute

@Serializable
object RegisterRoute

@Serializable
object LandingRoute

@Serializable
object TravelerRoute

@Serializable
object HomeRoute

@Serializable
object MoodFilterRoute

@Serializable
data class VideoPagerRoute (val selectedMoods: List<String>)

@Serializable
data class InteractionRoute (val selectedVideo: String)

@Serializable
data class BookingRoute (val selectedVideo: String)

@Composable
fun MoodScapeRoutes(
    navController: NavHostController,
    authenticated: Boolean = false,
    role: String,
    onLogoutSuccess: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var startDestination: Any = LandingRoute
    var navigateRoute: Any = LandingRoute


    Log.d("MoodScapeRoutes", "authenticated: $authenticated")
    if (!authenticated) {
        startDestination = AuthenticationRoute
        navigateRoute = LandingRoute
    } else {
        when (role) {
            "traveler" -> {
                startDestination = TravelerRoute
                navigateRoute = TravelerRoute
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        navigation<AuthenticationRoute>(startDestination = LoginRoute) {
            composable<RegisterRoute> {
                RegisterScreen(
                    onRegisterComplete = {
                        navController.navigate(navigateRoute) {
                            popUpTo(AuthenticationRoute) {
                                inclusive = true
                            }
                        }
                    },
                    onLoginClick = {
                        navController.navigate(LoginRoute) {
                            popUpTo(AuthenticationRoute) {
                                inclusive = true
                            }
                        }
                    })
            }
            composable<LoginRoute> {
                LoginScreen(
                    onLoginComplete = { role ->
                        navController.navigate(navigateRoute) {
                            popUpTo(AuthenticationRoute) {
                                inclusive = true
                            }
                        }
                    },
                    onRegisterClick = {
                        navController.navigate(RegisterRoute) {
                            popUpTo(AuthenticationRoute) {
                                inclusive = true
                            }
                        }
                    })
            }
        }

        navigation<TravelerRoute>(startDestination = MoodFilterRoute) {
            composable<MoodFilterRoute> {
                MoodScreen(
                    onSkip = {
                        navController.navigate(HomeRoute)
                    },
                    onSelected = { selectedMoodIds ->
                        navController.navigate(VideoPagerRoute(selectedMoodIds))
                    }
                )
            }

            composable<VideoPagerRoute> { backStackEntry ->
                val route: VideoPagerRoute = backStackEntry.toRoute()
                Log.d("VideoPagerRoute", "selectedMoods: ${route.selectedMoods}")
                VideoPagerScreen(
                    selectedMoods = route.selectedMoods,
                    onHomeCtaClick = {
                        navController.navigate(HomeRoute)
                    }
                )
            }

            composable<InteractionRoute> { backStackEntry ->
                val route: InteractionRoute = backStackEntry.toRoute()
                Log.d("InteractionRoute", "selectedVid: ${route.selectedVideo}")
                VideoActionBar(
                    likes = 0,
                    comments = 0,
                    onLikeClick = {
                        navController.navigate(BookingRoute)
                    },
                    onCommentClick = {},
                    onSaveClick = {}
                )
            }

            composable<HomeRoute> {
                HomeScreen(
                    onScheduleClick = {},
                    onDonationClick = { id -> },
                    onLogout = {
                        navController.navigate(AuthenticationRoute) {
                            popUpTo(LandingRoute) {
                                inclusive = true
                            }
                        }
                        onLogoutSuccess()
                    })
            }

            composable<BookingRoute> {
//                BookingScreen()
            }
        }

        composable<LandingRoute> {
            LandingScreen(onLogout = {
                navController.navigate(AuthenticationRoute) {
                    popUpTo(LandingRoute) {
                        inclusive = true
                    }
                }
            })
        }
    }
}