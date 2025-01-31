package com.nitt.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nitt.administration.screens.AdminCreateAPostScreen
import com.nitt.administration.screens.AdminDashBoard
import com.nitt.administration.screens.AdminHomeScreen
import com.nitt.administration.screens.AdminLoginScreen
import com.nitt.administration.screens.AdminLoginVerificationScreen
import com.nitt.administration.screens.AdminViewPostScreen
import com.nitt.administration.viewModels.PostRepository
import com.nitt.alumni.screens.AlumniCommunityScreen
import com.nitt.alumni.screens.AlumniCreateAPostScreen
import com.nitt.alumni.screens.AlumniHomeScreen
import com.nitt.alumni.screens.AlumniLoginScreen
import com.nitt.alumni.screens.AlumniPostViewScreen
import com.nitt.alumni.screens.AlumniRegisterScreen
import com.nitt.common.navigationDeck.adminAndAlumniScaffold
import com.nitt.common.screens.CommonSplashScreen
import com.nitt.common.screens.UserRoleSelectionScreen
import com.nitt.student.navigationDeck.Page
import com.nitt.student.navigationDeck.Search
import com.nitt.student.screens.StarZeroxScreen
import com.nitt.student.screens.StudentAdminDashboardScreen
import com.nitt.student.screens.StudentAdminPostViewScreen
import com.nitt.student.screens.StudentAlumniCommunityScreen
import com.nitt.student.screens.StudentAlumniPostViewScreen
import com.nitt.student.screens.StudentBookMarkScreen
import com.nitt.student.screens.StudentCalendarScreen
import com.nitt.student.screens.StudentClubDirectoryScreen
import com.nitt.student.screens.StudentCreateAPostScreen
import com.nitt.student.screens.StudentFestDirectoryScreen
import com.nitt.student.screens.StudentHomeScreen
import com.nitt.student.screens.StudentLoginScreen
import com.nitt.student.screens.StudentProfileScreen
import com.nitt.student.screens.buildUrl
import com.nitt.webView.WebView
import com.nitt.webView.Webmail

@Composable
fun Almamater() {
    val navController = rememberNavController()
    val selectedIndex  = remember { mutableIntStateOf(0) }
    NavHost(
        navController = navController,
        startDestination = Screens.CommonSplashScreen.route
    ) {
        composable(
            route = Screens.WebViewScreen.route+"/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        )
        { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url")
            val decodedUrl = Uri.decode(encodedUrl)
            if (decodedUrl != null) {
                WebView(url = decodedUrl)
            }
        }
        composable(Screens.CommonSplashScreen.route) {
            CommonSplashScreen(navController)
        }
        composable(Screens.UserRoleSelectionScreen.route) {
            UserRoleSelectionScreen(navController = navController)
        }
        // admin side
        composable(Screens.AdminLoginScreen.route) {
            AdminLoginScreen(navController)
        }
        composable(
            Screens.AdminLoginVerificationScreen.route+"/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            if (email != null) {
                AdminLoginVerificationScreen(email = email, navController = navController)
            }
        }
        composable(
            route = Screens.AdminHomeScreen.route,
        ) {
            adminAndAlumniScaffold(
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
                adminAndAlumniScaffold(
                    currentPage = {   AdminCreateAPostScreen(navController = navController,mode = mode) },
                    searchBar = false,
                    heading = "CREATE",
                    navController = navController
                )
            }
        }
        composable(Screens.AdminDashboardScreen.route) {
            val searchViewModel = Search()
            searchViewModel.updateSearchQuery(
                adminAndAlumniScaffold(
                    currentPage = { AdminDashBoard(navController = navController, searchViewModel) },
                    searchBar = true,
                    heading = "ADMIN DASHBOARD",
                    navController = navController
                )
            )
        }
        composable(
            route = Screens.AdminViewPostScreen.route + "/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            val postDetails = postId?.let { PostRepository.getPost(it) }

            adminAndAlumniScaffold(
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
                heading = "HOME",
                selectedIndex = selectedIndex
            )
        }
        composable(Screens.StudentCalendarScreen.route) {
            Page(
                currentPage ={  StudentCalendarScreen() },
                navController = navController,
                searchBar  = false,
                heading = "CALENDAR",
                selectedIndex = selectedIndex
            )
        }
        composable(Screens.StudentCreateAPostScreen.route) {
            Page(
                currentPage ={  StudentCreateAPostScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "CREATE A POST",
                selectedIndex = selectedIndex
            )
        }
        composable(Screens.StudentBookMarkScreen.route) {
            Page(
                currentPage ={  StudentBookMarkScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "BOOKMARK",
                selectedIndex = selectedIndex
            )
        }
        composable(Screens.StudentProfileScreen.route) {
            Page(
                currentPage ={  StudentProfileScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "PROFILE",
                selectedIndex = selectedIndex
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
                heading = "PROFILE",
                selectedIndex = selectedIndex
            )
        }
        composable(Screens.StudentAdminDashboardScreen.route) {
            Page(
                currentPage ={  StudentAdminDashboardScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "DASHBOARD",
                selectedIndex = selectedIndex
            )
        }
        composable(Screens.StudentFestDirectoryScreen.route) {
            Page(
                currentPage ={  StudentFestDirectoryScreen() },
                navController = navController,
                searchBar  = false,
                heading = "FEST DIRECTORY",
                selectedIndex = selectedIndex
            )
        }
        composable(Screens.StudentClubDirectoryScreen.route) {
            Page(
                currentPage ={  StudentClubDirectoryScreen() },
                navController = navController,
                searchBar  = false,
                heading = "CLUB DIRECTORY",
                selectedIndex = selectedIndex
            )
        }
        // alumni side
        composable(Screens.AlumniHomeScreen.route) {
            adminAndAlumniScaffold(
                currentPage ={ AlumniHomeScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "HOME"
            )
        }
        composable(Screens.AlumniCreateAPostScreen.route) {
            adminAndAlumniScaffold(
                currentPage ={  AlumniCreateAPostScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "CREATE"
            )
        }
        composable(Screens.AlumniLoginScreen.route) {
            AlumniLoginScreen(navController = navController)
        }
        composable(Screens.AlumniRegisterScreen.route) {
            AlumniRegisterScreen(navController = navController)
        }
        composable(Screens.AlumniCommunityScreen.route) {
            adminAndAlumniScaffold(
                currentPage ={  AlumniCommunityScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "COMMUNITY"
            )
        }
        composable(Screens.AlumniPostViewScreen.route) {
            adminAndAlumniScaffold(
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
                heading = "STAR XEROX",
                selectedIndex = selectedIndex
            )
        }
        composable(Screens.StudentAlumniCommunityScreen.route) {
            Page(
                currentPage ={  StudentAlumniCommunityScreen(navController = navController) },
                navController = navController,
                searchBar  = false,
                heading = "COMMUNITY",
                selectedIndex = selectedIndex
            )
        }
        composable(Screens.StudentAlumniPostViewScreen.route) {
            Page(
                currentPage ={  StudentAlumniPostViewScreen(navController) },
                navController = navController,
                searchBar  = false,
                heading = "POST",
                selectedIndex = selectedIndex
            )
        }
    }
}