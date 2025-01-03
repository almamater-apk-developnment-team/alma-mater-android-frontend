package com.journalia_nitt.journalia_admin_cms.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.journalia_nitt.journalia_admin_cms.common.screens.AdminComponents
import com.journalia_nitt.journalia_admin_cms.student.screens.AdminDetailsPage
import com.journalia_nitt.journalia_admin_cms.student.screens.CommunityPage
import com.journalia_nitt.journalia_admin_cms.common.screens.LoginPage
import com.journalia_nitt.journalia_admin_cms.student.screens.PostCreation
import com.journalia_nitt.journalia_admin_cms.student.screens.ProfilePage
import com.journalia_nitt.journalia_admin_cms.student.screens.BookmarkPage
import com.journalia_nitt.journalia_admin_cms.student.screens.CalenderPage
import com.journalia_nitt.journalia_admin_cms.student.screens.PDFWebViewScreen
import com.journalia_nitt.journalia_admin_cms.student.screens.ClubPage
import com.journalia_nitt.journalia_admin_cms.student.screens.ClubSubPage
import com.journalia_nitt.journalia_admin_cms.student.screens.FestDirectory
import com.journalia_nitt.journalia_admin_cms.student.screens.LandingPage
import com.journalia_nitt.journalia_admin_cms.student.screens.clubCommunityPage
import com.journalia_nitt.journalia_admin_cms.student.screens.ViewPost
import com.example.journalia.WebView.WebView
import com.example.journalia.WebView.Webmail
import com.example.journalia.WebView.buildUrl
import com.google.gson.Gson
import com.journalia_nitt.journalia_admin_cms.alumni.screens.AlumniPageContent
import com.journalia_nitt.journalia_admin_cms.alumni.screens.CommunityScreen
import com.journalia_nitt.journalia_admin_cms.common.screens.SplashScreen
import com.journalia_nitt.journalia_admin_cms.student.responses.Deadline
import com.journalia_nitt.journalia_admin_cms.student.responses.UserFetchClass
import com.journalia_nitt.journalia_admin_cms.student.webmailURL


@Composable
fun MyApp(innerPaddingValues: PaddingValues) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ) {
        composable(Screens.SplashScreen.route) {
            SplashScreen(innerPaddingValues,navController)
        }
        composable(Screens.LoginPage.route) {
            LoginPage(navController=navController)
        }
        composable(Screens.AuthPage.route) {
            WebView(navController = navController, url = buildUrl())
        }
        composable(Screens.HomePage.route) {
            Page(
                currentPage = {
                    LandingPage(navController)
                },
                navController = navController,
                searchBar = false,
                heading = "HOME",
            )
        }
        composable(Screens.CalenderPage.route) {
            Page(
                currentPage = {
                    CalenderPage()},
                navController = navController,
                searchBar = false,
                heading = "CALENDAR",
            )
        }
        composable(Screens.AdminPage.route) {
            Page(
                currentPage = {
                    AdminComponents(navController)
                },
                navController = navController,
                searchBar = false,
                heading = "ADMIN BOARD",
            )
        }
        composable(Screens.PostCreationPage.route) {
            Page(
                currentPage = {
                    PostCreation(navController)
                },
                navController = navController,
                searchBar = false,
                heading = "CREATE A POST",
            )
        }
        composable(Screens.BookMarkPage.route) {
            Page(
                currentPage = {
                    BookmarkPage()
                },
                navController = navController,
                searchBar = false,
                heading = "BOOKMARKS",
            )
        }
        composable(Screens.ProfilePage.route) {
            Page(
                currentPage = {
                    ProfilePage(navController)
                },
                navController = navController,
                searchBar = false,
                heading = "PROFILE",
            )
        }
        composable(
            route = Screens.AdminDetailsPage.route,
            arguments = listOf(
                navArgument("item") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->

            val itemJson = navBackStackEntry.arguments?.getString("item")
            val item = if (itemJson != null) {
                Gson().fromJson(itemJson, Deadline::class.java)
            } else {
                null
            }

            if (item != null) {
                Page(
                    currentPage = {
                        AdminDetailsPage(item,navController)
                    },
                    navController = navController,
                    searchBar = false,
                    heading = "CIRCULAR",
                )
            } else {
                Text("Error: Item not found")
            }
        }

        composable(Screens.FestDirectory.route) {
            Page(
                currentPage={
                    FestDirectory(navController)
                },
                navController = navController,
                searchBar = false,
                heading = "FEST DIRECTORY",
            )
        }
        composable(Screens.ClubDirectory.route) {
            Page(
                currentPage = {
                    ClubSubPage(navController)
                },
                navController = navController,
                searchBar = false,
                heading = "CLUB DIRECTORY",
            )
        }
        composable(Screens.ClubPage.route) {
            Page(
                currentPage = {
                    ClubPage(innerPaddingValues,navController)
                },
                navController = navController,
                searchBar = false,
                heading = "Explore",
            )
        }
        composable(Screens.CommunityPage.route) {
            Page(
                currentPage = {
                    CommunityPage(innerPaddingValues,navController)
                },
                navController = navController,
                searchBar = false,
                heading = "PUBLIC COMMUNITY",
            )
        }
        composable(Screens.Webmail.route) {
            Page(
                currentPage = {
                    Webmail(webmailURL)
                },
                navController = navController,
                searchBar = false,
                heading = "",
            )
        }
        composable(Screens.ClubCommunityPage.route) {
            Page(
                currentPage = {
                    clubCommunityPage(innerPaddingValues,navController)
                },
                navController = navController,
                searchBar = false,
                heading = "FEST COMMUNITY",
            )
        }
        composable(
            route= Screens.ViewPost.route,
            arguments = listOf(
                navArgument("item") {
                    type = NavType.StringType
                }
            )
        ) {navBackStackEntry ->
            val itemJson1 = navBackStackEntry.arguments?.getString("item")
            val item = if (itemJson1 != null) {
                Gson().fromJson(itemJson1, UserFetchClass::class.java)
            } else {
                null
            }

            if (item != null) {
                Page(
                    currentPage = {
                        ViewPost(navController,item)
                    },
                    navController = navController,
                    searchBar = false,
                    heading = "VIEW POST",
                )
            } else {
                Text("Error: Item not found")
            }
        }
        composable(Screens.AlumniCommunityScreen.route) {
            Page(
                currentPage = {
                    CommunityScreen(innerPaddingValues,navController)
                },
                navController = navController,
                searchBar = false,
                heading = "ALUMNI COMMUNITY",
            )
        }
        composable(Screens.PdfWebViewPage.route) {
            Page(
                currentPage = {
                    PDFWebViewScreen()
                },
                navController = navController,
                searchBar = false,
                heading = "PDF VIEW SCREEN",
            )
        }
        composable(Screens.AlumniContentPage.route) {
            val showBottomSheet = remember { mutableStateOf(false) }
            Page(
                currentPage = {
                    AlumniPageContent(innerPaddingValues,showBottomSheet,navController)
                },
                navController = navController,
                searchBar = false,
                heading = "POST SCREEN",
            )
        }
    }
}