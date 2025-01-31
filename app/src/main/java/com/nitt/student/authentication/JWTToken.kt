package com.nitt.student.authentication

import com.nitt.student.responses.LoginRequest
import com.nitt.student.responses.TokenResponse
import com.nitt.api.RetrofitClient
import com.nitt.student.services.StudentApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
