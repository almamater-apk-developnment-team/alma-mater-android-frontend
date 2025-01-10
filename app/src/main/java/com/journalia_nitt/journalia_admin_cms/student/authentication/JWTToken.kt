package com.journalia_nitt.journalia_admin_cms.student.authentication

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.journalia_nitt.journalia_admin_cms.student.responses.LoginRequest
import com.journalia_nitt.journalia_admin_cms.student.responses.TokenResponse
import com.journalia_nitt.journalia_admin_cms.api.RetrofitClient
import com.journalia_nitt.journalia_admin_cms.student.bookMarkHandle
import com.journalia_nitt.journalia_admin_cms.student.services.StudentApiService
import com.journalia_nitt.journalia_admin_cms.student.sharedPreferences.saveTokenDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JWTToken {
    fun generateJWT(rollno: String, context:Context){

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Call the suspend function within the coroutine
                val response = bookMarkHandle.getStudentToken(rollno).token

                CoroutineScope(Dispatchers.Main).launch {
                    Log.d("successFileFetchFromUser", response)
                    saveTokenDetails(context,response)

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
