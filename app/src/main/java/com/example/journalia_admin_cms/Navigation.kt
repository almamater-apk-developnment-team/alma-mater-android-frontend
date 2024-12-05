package com.example.journalia_admin_cms

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


@Composable
fun MyApp(innerPaddingValues: PaddingValues) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.SplashPage.route) {
        composable(
            route = Screens.SplashPage.route,
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 200 , easing = EaseOut))
            }
        ) {
            SplashPage(innerPaddingValues , navController)
        }
        composable(
            route = Screens.LoginPage.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 200 , easing = EaseIn))
            },
        ) {
            LoginPage(innerPaddingValues , navController)
        }
        composable(
            route = Screens.SecretPage.route,
            arguments = listOf(
                navArgument("email") {type = NavType.StringType}
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            SecretChecking(email , innerPaddingValues , navController)
        }
        composable(
            route = Screens.LandingPage.route,
        ) {
            LandingPage(innerPaddingValues , navController)
        }
        composable(route = Screens.AdminPage.route) {
            AdminDashBoard(innerPaddingValues , navController)
        }
        composable(route = Screens.DeadlinePage.route) {
            PostPage(innerPaddingValues , navController)
        }
        composable(route = Screens.AnnouncementPage.route) {
            PostPage(innerPaddingValues , navController)
        }
        composable(route = Screens.InfoPage.route) {
            AdminInfo(innerPaddingValues , navController)
        }

    }
}