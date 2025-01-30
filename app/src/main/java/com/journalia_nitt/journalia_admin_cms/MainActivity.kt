package com.journalia_nitt.journalia_admin_cms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.journalia_nitt.journalia_admin_cms.student.viewModels.PostDeadLine
import com.journalia_nitt.journalia_admin_cms.navigation.MyApp

class MainActivity : ComponentActivity() {
    private val postDeadLine = PostDeadLine()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
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

