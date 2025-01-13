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
import vn.edu.rmit.ui.screen.LandingScreen
import vn.edu.rmit.ui.screen.auth.login.LoginScreen
import vn.edu.rmit.ui.screen.auth.register.RegisterScreen
import vn.edu.rmit.ui.screen.user.booking.BookingScreen
import vn.edu.rmit.ui.screen.user.filter.MoodScreen
import vn.edu.rmit.ui.screen.user.home.HomeScreen
import vn.edu.rmit.ui.screen.user.property.PropertyScreen
import vn.edu.rmit.ui.screen.user.reels.SlideVideoPagerScreen

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
data class SlideVideoPagerRoute (val selectedMoods: List<String>)

@Serializable
data class PropertyRoute(val id: String)

@Serializable
data class BookingRoute(val id:String)

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
                    }
                )
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
                        navController.navigate(SlideVideoPagerRoute(selectedMoodIds))
                    }
                )
            }

            composable<SlideVideoPagerRoute> { backStackEntry ->
                val route: SlideVideoPagerRoute = backStackEntry.toRoute()
                Log.d("VideoPagerRoute", "selectedMoods: ${route.selectedMoods}")
                SlideVideoPagerScreen(
                    onHomeCtaClick = {
                        navController.navigate(HomeRoute)
                    },
                    onDetailClick = { navController.navigate(PropertyRoute(it)) },
                    onBookingClick = { navController.navigate(BookingRoute(it)) },
                    selectedMoods = route.selectedMoods
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

            composable<PropertyRoute> { backStackEntry ->
                val route: PropertyRoute = backStackEntry.toRoute()

                PropertyScreen(route.id)
            }

            composable<BookingRoute> {backStackEntry ->
                val route: BookingRoute = backStackEntry.toRoute()

                BookingScreen(route.id)

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