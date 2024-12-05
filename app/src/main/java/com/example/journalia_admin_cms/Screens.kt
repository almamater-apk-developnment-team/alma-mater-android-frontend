package com.example.journalia_admin_cms

sealed class Screens(val route : String) {
    object LoginPage : Screens("loginPage")
    object SecretPage : Screens("secretPage/{email}") {
        fun createRoute(email : String) = "secretPage/$email"
    }
    object SplashPage : Screens("splashPage")
    object LandingPage : Screens("landingPage")
    object AdminPage : Screens("adminPage/{token}") {
        fun createRoute(token : String) = "adminPage/$token"
    }
    object DeadlinePage : Screens("deadlinePage")
    object AnnouncementPage : Screens("announcementPage")
    object InfoPage : Screens("infoPage")
}