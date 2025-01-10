package com.journalia_nitt.journalia_admin_cms

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.journalia_nitt.journalia_admin_cms.student.sharedPreferences.getUserDetails
import com.journalia_nitt.journalia_admin_cms.firebase.fcmTokenToDataStore
import com.journalia_nitt.journalia_admin_cms.student.viewModels.PostDeadLine
import com.journalia_nitt.journalia_admin_cms.student.responses.DeadlineRequest
import com.journalia_nitt.journalia_admin_cms.student.sharedPreferences.saveTokenDetails
import com.journalia_nitt.journalia_admin_cms.alumni.response.LoggedInAccount
import com.journalia_nitt.journalia_admin_cms.navigation.MyApp
import com.journalia_nitt.journalia_admin_cms.student.authentication.JWTToken
import com.journalia_nitt.journalia_admin_cms.student.viewModels.bookMarkViewModel

class MainActivity : ComponentActivity() {
    private val authRepository = JWTToken()
    private val postDeadLine = PostDeadLine()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val userDetails = getUserDetails(context = context)
            val rollno = userDetails?.collegeId.toString()
            if (userDetails != null) {
                authRepository.generateJWT(rollno,context).toString()
            }
//            val deadlineRequest = DeadlineRequest(deadline = "2024-12-31")
//            postDeadLine.postDeadLine(deadlineRequest = deadlineRequest, context =  context)
//            LaunchedEffect(Unit) {
//                fcmTokenToDataStore(context = context)
//            }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   MyApp(innerPadding)
                }

        }
    }
}

