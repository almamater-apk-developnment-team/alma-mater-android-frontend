package com.example.journalia_admin_cms

sealed class Screens(val route : String) {
    object LoginPage : Screens("loginPage")
    object SecretPage : Screens("secretPage/{email}") {
        fun createRoute(email : String) = "secretPage/$email"
    }
    object SplashPage : Screens("splashPage")
    object LandingPage : Screens("landingPage/{token}"){
        fun createRoute(token : String) = "landingPage/$token"
    }
    object AdminPage : Screens("adminPage/{token}") {
        fun createRoute(token : String) = "adminPage/$token"
    }
    object DeadlinePage : Screens("deadlinePage/{token}") {
        fun createRoute(token : String) = "deadlinePage/$token"
    }
    object AnnouncementPage : Screens("announcementPage/{token}"){
        fun createRoute(token : String) = "announcementPage/$token"
    }
    object InfoPage : Screens("infoPage")
}