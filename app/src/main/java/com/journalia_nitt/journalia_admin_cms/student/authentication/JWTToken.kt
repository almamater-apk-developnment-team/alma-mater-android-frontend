package com.journalia_nitt.journalia_admin_cms.student.authentication

import android.content.Context
import android.util.Log
import com.journalia_nitt.journalia_admin_cms.student.sharedPreferences.saveUserLoginToken
import com.journalia_nitt.journalia_admin_cms.student.viewModels.uploadClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class JWTToken {
    fun generateJWT(rollno: String, context:Context){

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Call the suspend function within the coroutine
                val response = uploadClient.getStudentToken(rollno).token

                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("successFileFetchFromUser", response)
                    saveUserLoginToken(context,response)

                }
            } catch (e: Exception) {
                // Handle errors
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("errorFileFetchFromUser", e.message.toString())
                }
            }
        }
    }
}
