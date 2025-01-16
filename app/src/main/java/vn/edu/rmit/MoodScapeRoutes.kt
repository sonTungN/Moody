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
import vn.edu.rmit.data.model.type.Mood
import vn.edu.rmit.ui.screen.LandingScreen
import vn.edu.rmit.ui.screen.auth.login.LoginScreen
import vn.edu.rmit.ui.screen.auth.register.RegisterScreen
import vn.edu.rmit.ui.screen.owner.OwnerPropertyScreen
import vn.edu.rmit.ui.screen.owner.OwnerReservedPropertyScreen
import vn.edu.rmit.ui.screen.user.booking.BookingScreen
import vn.edu.rmit.ui.screen.user.filter.MoodScreen
import vn.edu.rmit.ui.screen.user.home.HomeScreen
import vn.edu.rmit.ui.screen.user.location.LocationScreen
import vn.edu.rmit.ui.screen.user.property.PropertyScreen
import vn.edu.rmit.ui.screen.user.property.SavedPropertyScreen
import vn.edu.rmit.ui.screen.user.reels.SlideVideoPagerScreen
import vn.edu.rmit.ui.screen.user.reserve.ReserveScreen
import vn.edu.rmit.ui.screen.user.settings.SettingScreen

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
object PropertyLocationRoute

@Serializable
object OwnerRoute

@Serializable
object MoodFilterRoute

@Serializable
object SettingsRoute

@Serializable
object ReservationRoute

@Serializable
object SavePropertyRoute

@Serializable
object PaymentRoute

@Serializable
data class SlideVideoPagerRoute(val selectedMoods: List<String>)

@Serializable
data class PropertyRoute(val id: String)

@Serializable
data class BookingRoute(val id: String)

@Serializable
object OwnerHomeRoute

@Serializable
object OwnerPropertyRoute

@Serializable
object OwnerReservePropertyRoute

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
            "owner" -> {
                startDestination = OwnerRoute
                navigateRoute = OwnerRoute
            }

            "manager" -> {
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

        navigation<OwnerRoute>(startDestination = OwnerHomeRoute) {
            composable<OwnerHomeRoute> {
                HomeScreen(
                    onReservationClick = { },
                    onDonationClick = { }
                )
            }

            composable<OwnerPropertyRoute> {
                OwnerPropertyScreen()
            }

            composable<OwnerReservePropertyRoute> {
                OwnerReservedPropertyScreen()
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
                SlideVideoPagerScreen(
                    initialSelectedMoods = route.selectedMoods,
                    onHomeCtaClick = {
                        navController.navigate(HomeRoute)
                    },
                    onDetailClick = { navController.navigate(PropertyRoute(it)) },
                    onBookingClick = { navController.navigate(BookingRoute(it)) },
                )
            }

            composable<HomeRoute> {
                HomeScreen(
                    onReservationClick = {},
                    onDonationClick = { id -> },
                )
            }

            composable<PropertyLocationRoute> {
                LocationScreen(
                    onPropertyClick = { property ->
                        navController.navigate(PropertyRoute(id = property.id))
                    }
                )
            }

            composable<ReservationRoute> {
                ReserveScreen()
            }

            composable<PropertyRoute> { backStackEntry ->
                val route: PropertyRoute = backStackEntry.toRoute()

                PropertyScreen(route.id)
            }

            composable<BookingRoute> { backStackEntry ->
                val route: BookingRoute = backStackEntry.toRoute()
                BookingScreen(
                    route.id,
                    onReservedClick = { navController.navigate(PaymentRoute)}
                )
            }

            composable<SavePropertyRoute> {
                SavedPropertyScreen()
            }

            composable<PaymentRoute> {

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

        composable<SettingsRoute> {
            SettingScreen(
                onLogoutSuccess = {
                    navController.navigate(AuthenticationRoute) {
                        popUpTo(LandingRoute) {
                            inclusive = true
                        }
                    }
                    onLogoutSuccess()
                }
            )
        }
    }
}