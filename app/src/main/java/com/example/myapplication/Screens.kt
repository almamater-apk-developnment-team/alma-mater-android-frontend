package com.example.myapplication

sealed class Screens(val route : String) {
    object SplashPage : Screens("splashPage")
    object LandingPage : Screens("landingPage")
    object AdminPage : Screens("adminPage")
    object DeadlinePage : Screens("deadlinePage")
    object AnnouncementPage : Screens("announcementPage")
    object InfoPage : Screens("infoPage")
}