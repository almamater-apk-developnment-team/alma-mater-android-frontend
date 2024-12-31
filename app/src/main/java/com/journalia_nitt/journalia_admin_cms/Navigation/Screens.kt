package com.example.journalia.Navigation

import com.google.gson.Gson
import android.net.Uri
import com.example.journalia.Student.Deadline
import com.example.journalia.Student.Responses.UserFetchClass

sealed class Screens(val route : String) {
    object CalenderPage : Screens("CalenderPage")
    object AdminPage : Screens("AdminPage")
    object PostCreationPage : Screens("PostCreationPage")
    object BookMarkPage : Screens("BookmarkPage")
    object ProfilePage : Screens("ProfilePage")
    object LoginPage : Screens("LoginPage")
    object CustomReminderPage : Screens("CustomReminderPage")
    object AdminDetailsPage : Screens("AdminDetailsPage/{item}") {
        fun createRoute(item: Deadline): String {
            val itemJson = Uri.encode(Gson().toJson(item))
            return "AdminDetailsPage/$itemJson"
        }
    }
    object FestDirectory : Screens("FestDirectory")
    object AuthPage : Screens("AuthPage")
    object HomePage: Screens("HomePage")
    object ClubDirectory: Screens("ClubDirectory")
    object CommunityPage: Screens("CommunityPage")
    object ClubPage: Screens("ClubPage")
    object Webmail : Screens("Webmail")
    object SplashScreen : Screens("SplashScreen")
    object ClubCommunityPage : Screens("ClubCommunityPage")
    object ViewPost : Screens("ViewPost/{item}"){
        fun createRoute(item: UserFetchClass): String {
            val itemJson1 = Uri.encode(Gson().toJson(item))
            return "ViewPost/$itemJson1"
        }
    }
    object AlumniCommunityScreen : Screens("AlumniCommunityScreen")
    object PdfWebViewPage : Screens("PdfWebViewPage")
    object AlumniContentPage : Screens("AlumniPageContent")
}