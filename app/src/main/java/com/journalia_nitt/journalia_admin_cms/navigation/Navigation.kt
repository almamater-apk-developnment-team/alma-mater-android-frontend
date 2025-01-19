package com.journalia_nitt.journalia_admin_cms.navigation

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.journalia_nitt.journalia_admin_cms.administration.response.AdminPost
import com.journalia_nitt.journalia_admin_cms.administration.screens.AdminCreateAPostScreen
import com.journalia_nitt.journalia_admin_cms.administration.screens.AdminDashBoard
import com.journalia_nitt.journalia_admin_cms.administration.screens.AdminHomeScreen
import com.journalia_nitt.journalia_admin_cms.administration.screens.AdminLoginScreen
import com.journalia_nitt.journalia_admin_cms.administration.screens.AdminLoginVerificationScreen
import com.journalia_nitt.journalia_admin_cms.administration.screens.AdminViewPostScreen
import com.journalia_nitt.journalia_admin_cms.administration.viewModels.PostRepository
import com.journalia_nitt.journalia_admin_cms.alumni.screens.AlumniCommunityScreen
import com.journalia_nitt.journalia_admin_cms.alumni.screens.AlumniCreateAPostScreen
import com.journalia_nitt.journalia_admin_cms.alumni.screens.AlumniHomeScreen
import com.journalia_nitt.journalia_admin_cms.alumni.screens.AlumniLoginScreen
import com.journalia_nitt.journalia_admin_cms.alumni.screens.AlumniPostViewScreen
import com.journalia_nitt.journalia_admin_cms.alumni.screens.AlumniRegisterScreen
import com.journalia_nitt.journalia_admin_cms.common.navigationDeck.AdminAndAlumniScaffold
import com.journalia_nitt.journalia_admin_cms.common.screens.CommonSplashScreen
import com.journalia_nitt.journalia_admin_cms.common.screens.UserRoleSelectionScreen
import com.journalia_nitt.journalia_admin_cms.student.navigationDeck.Page
import com.journalia_nitt.journalia_admin_cms.student.responses.Deadline
import com.journalia_nitt.journalia_admin_cms.student.responses.Post
import com.journalia_nitt.journalia_admin_cms.student.screens.StarZeroxScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.StudentAdminDashboardScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.StudentAdminPostViewScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.StudentAlumniCommunityScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.StudentAlumniPostViewScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.StudentBookMarkScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.StudentCalendarScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.StudentClubDirectoryScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.StudentCreateAPostScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.StudentFestDirectoryScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.StudentHomeScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.StudentLoginScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.StudentProfileScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.buildUrl
import com.journalia_nitt.journalia_admin_cms.webView.WebView
import com.journalia_nitt.journalia_admin_cms.webView.Webmail

@Composable
fun MyApp(innerPaddingValues: PaddingValues) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.StudentClubDirectoryScreen.route
    ) {
        composable(
            route = Screens.WebViewScreen.route+"/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        )
        { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url")
            if (url != null) {
                WebView(url = url)
            }
        }
        composable(Screens.CommonSplashScreen.route) {
            CommonSplashScreen(innerPaddingValues,navController)
        }
        composable(Screens.UserRoleSelectionScreen.route) {
            UserRoleSelectionScreen(navController = navController)
        }
        // admin side
        composable(Screens.AdminLoginScreen.route) {
            AdminLoginScreen(innerPaddingValues,navController)
        }
        composable(
            Screens.AdminLoginVerificationScreen.route+"/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            if (email != null) {
                AdminLoginVerificationScreen(email = email, navController = navController, innerPaddingValues = innerPaddingValues)
            }
        }
        composable(
            route = Screens.AdminHomeScreen.route,
        ) {
            AdminAndAlumniScaffold(
                currentPage = {   AdminHomeScreen(navController = navController) },
                searchBar = false,
                heading = "HOME",
                navController = navController
            )
        }
        composable(
            Screens.AdminCreatePostScreen.route+"/{mode}",
            arguments = listOf(navArgument("mode") { type = NavType.IntType })
        ) {backStackEntry ->
            val mode = backStackEntry.arguments?.getInt("mode")
            if (mode != null) {
                AdminAndAlumniScaffold(
                    currentPage = {   AdminCreateAPostScreen(navController = navController,mode = mode) },
                    searchBar = false,
                    heading = "CREATE",
                    navController = navController
                )
            }
        }
        composable(Screens.AdminDashboardScreen.route) {
            AdminAndAlumniScaffold(
                currentPage = { AdminDashBoard(navController = navController) },
                searchBar = true,
                heading = "ADMIN DASHBOARD",
                navController = navController
            )
        }
        composable(
            route = Screens.AdminViewPostScreen.route + "/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            val postDetails = postId?.let { PostRepository.getPost(it) }

            AdminAndAlumniScaffold(
                currentPage = {
                    if (postDetails != null) {
                        AdminViewPostScreen(navController = navController, adminPost = postDetails)
                    }
                },
                searchBar = false,
                heading = "CIRCULAR",
                navController = navController
            )
        }

        // student side
        composable(Screens.StudentLoginScreen.route) {
            StudentLoginScreen(navController, buildUrl())
        }
        composable(Screens.StudentHomeScreen.route) {
            Page(
                currentPage ={ StudentHomeScreen(navController) },
                navController = navController,
                searchBar  = false,
                heading = "HOME"
            )
        }
        composable(Screens.StudentCalendarScreen.route) {
            Page(
                currentPage ={  StudentCalendarScreen() },
                navController = navController,
                searchBar  = false,
                heading = "CALENDAR"
            )
        }
        composable(Screens.StudentCreateAPostScreen.route) {
            Page(
                currentPage ={  StudentCreateAPostScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "CREATE A POST"
            )
        }
        composable(Screens.StudentBookMarkScreen.route) {
            Page(
                currentPage ={  StudentBookMarkScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "BOOKMARK"
            )
        }
        composable(Screens.StudentProfileScreen.route) {
            Page(
                currentPage ={  StudentProfileScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "PROFILE"
            )
        }
        composable(
            route = Screens.StudentAdminPostViewScreen.route + "/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
            ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            val postDetails = postId?.let { PostRepository.getPost(it) }
            Page(
                currentPage ={
                    if (postDetails != null) {
                        StudentAdminPostViewScreen(navController = navController, adminPost = postDetails)
                    }
                },
                navController = navController,
                searchBar  = false,
                heading = "PROFILE"
            )
        }
        composable(Screens.StudentAdminDashboardScreen.route) {
            Page(
                currentPage ={  StudentAdminDashboardScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "DASHBOARD"
            )
        }
        composable(Screens.StudentFestDirectoryScreen.route) {
            Page(
                currentPage ={  StudentFestDirectoryScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "FEST DIRECTORY"
            )
        }
        composable(Screens.StudentClubDirectoryScreen.route) {
            Page(
                currentPage ={  StudentClubDirectoryScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "CLUB DIRECTORY"
            )
        }
        // alumni side
        composable(Screens.AlumniHomeScreen.route) {
            AdminAndAlumniScaffold(
                currentPage ={ AlumniHomeScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "HOME"
            )
        }
        composable(Screens.AlumniCreateAPostScreen.route) {
            AdminAndAlumniScaffold(
                currentPage ={  AlumniCreateAPostScreen(navController = navController, innerPaddingValues = innerPaddingValues) },
                navController = navController,
                searchBar  = false,
                heading = "CREATE"
            )
        }
        composable(Screens.AlumniLoginScreen.route) {
            AlumniLoginScreen(navController = navController, innerPaddingValues = innerPaddingValues)
        }
        composable(Screens.AlumniRegisterScreen.route) {
            AlumniRegisterScreen(navController = navController, innerPaddingValues = innerPaddingValues)
        }
        composable(Screens.AlumniCommunityScreen.route) {
            AdminAndAlumniScaffold(
                currentPage ={  AlumniCommunityScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "COMMUNITY"
            )

        }
        composable(Screens.AlumniPostViewScreen.route) {
            AdminAndAlumniScaffold(
                currentPage ={   AlumniPostViewScreen(navController = navController,) },
                navController = navController,
                searchBar  = false,
                heading = "POST"
            )
        }
        composable(Screens.WebMailScreen.route) {
            Webmail(url = "https://students.nitt.edu/horde/login.php")
        }
        // Student Services
        composable(Screens.StarXeroxScreen.route) {
            Page(
                currentPage ={  StarZeroxScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "STAR XEROX"
            )
        }
        composable(Screens.StudentAlumniCommunityScreen.route) {
            Page(
                currentPage ={  StudentAlumniCommunityScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "COMMUNITY"
            )
        }
        composable(Screens.StudentAlumniPostViewScreen.route) {
            Page(
                currentPage ={  StudentAlumniPostViewScreen(navController) },
                navController = navController,
                searchBar  = false,
                heading = "POST"
            )
        }
    }
}