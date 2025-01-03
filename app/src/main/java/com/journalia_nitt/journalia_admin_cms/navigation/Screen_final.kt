package com.journalia_nitt.journalia_admin_cms.navigation

sealed class ScreenFinal(val route:String) {
    // admin side
    data object AdminCardScreen : ScreenFinal("AdminCardScreen")
    data object AdminCreatePostScreen : ScreenFinal("AdminCreatePostScreen")
    data object AdminDashboardScreen : ScreenFinal("AdminDashboardScreen")
    data object AdminLoginScreen : ScreenFinal("AdminLoginScreen")
    data object AdminLoginVerificationScreen : ScreenFinal("AdminVerificationScreen")
    data object AdminSplashScreen : ScreenFinal("AdminSplashScreen")
    data object AdminViewPostScreen : ScreenFinal("AdminViewPostScreen")

    //    Alumni Side
    data object AlumniCreateAPostScreen : ScreenFinal("AlumniCreateAPostScreen")
    data object AlumniLoginScreen : ScreenFinal("AlumniLoginScreen")
    data object AlumniRegisterScreen : ScreenFinal("AlumniRegisterScreen")
    data object AlumniSplashScreen : ScreenFinal("AlumniSplashScreen")
    data object AlumniPostViewScreen: ScreenFinal("AlumniPostViewScreen")
    data object AlumniHomeScreen: ScreenFinal("AlumniHomeScreen")

    //    Student Side
    data object StudentAdminPostViewScreen : ScreenFinal("StudentAdminPostViewScreen")
    data object StudentRegisterScreen : ScreenFinal("StudentRegisterScreen")
    data object StudentSplashScreen : ScreenFinal("StudentSplashScreen")
    data object StudentBookMarkScreen : ScreenFinal("StudentBookMarkScreen")
    data object StudentCalendarScreen : ScreenFinal("StudentCalendarScreen")
    data object StudentCreateAPostScreen : ScreenFinal("StudentCreateAPostScreen")
    data object StudentHomeScreen : ScreenFinal("StudentSplashScreen")
    data object StudentInboxScreen : ScreenFinal("StudentInboxScreen")
    data object StudentProfileScreen : ScreenFinal("StudentProfileScreen")
    data object StudentPublicCommunityPostViewScreen : ScreenFinal("StudentPublicCommunityPostViewScreen")
    data object StudentPublicCommunityScreen : ScreenFinal("StudentPublicCommunityScreen")
}