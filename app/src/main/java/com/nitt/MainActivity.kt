package com.nitt

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import com.nitt.navigation.Almamater
import com.nitt.permissions.Permissions
import com.nitt.student.sharedPreferences.getUserDetails
import com.nitt.student.sharedPreferences.saveTokenDetails
import com.nitt.student.authentication.JWTToken

class MainActivity : ComponentActivity() {
    private val authRepository = JWTToken()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val activity = LocalContext.current as? Activity
            activity?.let {
                val permissions = Permissions(it)
                permissions.requestNotificationPermissions()
                permissions.createNotificationChannel()
            }
            val context = LocalContext.current
            val userDetails = getUserDetails(context = context)
            if (userDetails != null) {
                authRepository.getToken(userDetails.collegeId) { result ->
                    result.onSuccess { token ->
                        Log.d("Token",token)
                        saveTokenDetails(context = context, token = token)
                    }.onFailure { error ->
                        Log.d("Token",error.message.toString())
                    }
                }
            }
            Almamater()
        }
    }
}



