package com.example.smilecare.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.smilecare.ui.add.AddScreen
import com.example.smilecare.ui.add.DestinasiBooking
import com.example.smilecare.ui.detail.DetailDestination
import com.example.smilecare.ui.detail.DetailScreen
import com.example.smilecare.ui.edit.EditDestination
import com.example.smilecare.ui.edit.EditScreen
import com.example.smilecare.ui.home.DestinasiHome
import com.example.smilecare.ui.home.HomeScreen
import com.example.smilecare.ui.home.LandingScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {

    NavHost(
        navController = navController,
        startDestination = DestinasiLanding.route,
        modifier = Modifier
    ) {
        composable(DestinasiLanding.route) {
            LandingScreen(
                onLoginClicked = {
                    navController.navigate(DestinasiHome.route)
                }
            )
        }
        composable(
            DestinasiHome.route
        ) {
            HomeScreen(navigateToItemEntry = {
                navController.navigate(DestinasiBooking.route)
            },
                onDetailClick = { itemId ->
                    navController.navigate("${DetailDestination.route}/$itemId")
                    println("itemId: $itemId")
                })
        }
        composable(DestinasiBooking.route) {
            AddScreen(navigateBack = {
                navController.popBackStack()
            })

        }

        composable(
            route = DetailDestination.routeWithArgs,
            arguments = listOf(navArgument(DetailDestination.bookingId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getString(DetailDestination.bookingId)
            bookingId?.let {
                DetailScreen(
                    navigateBack = { navController.popBackStack() },
                    navigateToEditItem = {
                        navController.navigate("${EditDestination.route}/$bookingId")
                        println("bookingId: $bookingId")
                    }
                )
            }
        }

        composable(
            route = EditDestination.routeWithArgs,
            arguments = listOf(navArgument(EditDestination.bookingId) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getString(EditDestination.bookingId)
            bookingId?.let {
                EditScreen(
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}