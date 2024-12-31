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
import androidx.navigation.compose.rememberNavController
import com.example.journalia.Alumni.Services.LoggedInAccount
import com.example.journalia.Firebase.fcmTokenToDataStore
import com.example.journalia.Student.Authentication.JWTToken
import com.example.journalia.Student.PostDeadLine
import com.example.journalia.Student.Responses.DeadlineRequest
import com.example.journalia.Student.SharedPreferences.saveTokenDetails
import com.journalia_nitt.journalia_admin_cms.ui.theme.MyApplicationTheme
var theUser = LoggedInAccount(
    username = "",
    email = "",
    designation = ""
)
class MainActivity : ComponentActivity() {
    private val authRepository = JWTToken()
    private val postDeadLine = PostDeadLine()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            authRepository.getToken("user1") { result ->
                result.onSuccess { token ->
                    Log.d("Token",token)
                    saveTokenDetails(context = context, token = token)
                }.onFailure { error ->
                    Log.d("Token",error.message.toString())
                }
            }
            val deadlineRequest = DeadlineRequest(deadline = "2024-12-31")
            postDeadLine.postDeadLine(deadlineRequest = deadlineRequest, context =  context)
            LaunchedEffect(Unit) {
                fcmTokenToDataStore(context = context)
            }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    com.example.journalia.Navigation.MyApp(innerPadding)
//                    AlarmNotificationUI(this,this,innerPadding)
                }

        }
    }
}

