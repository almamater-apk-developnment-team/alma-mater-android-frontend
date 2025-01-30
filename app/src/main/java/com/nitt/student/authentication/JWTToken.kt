package com.nitt.student.authentication

import com.nitt.student.responses.LoginRequest
import com.nitt.student.responses.TokenResponse
import com.nitt.api.RetrofitClient
import com.nitt.student.services.StudentApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JWTToken {
    private val apiService = RetrofitClient.instance.create(StudentApiService::class.java)
    fun getToken(name: String, callback: (Result<String>) -> Unit) {
        val loginRequest = LoginRequest(name)
        apiService.getToken(loginRequest).enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    if (token != null) {
                        callback(Result.success(token))
                    } else {
                        callback(Result.failure(Exception("Token is null")))
                    }
                } else {
                    callback(Result.failure(Exception("Error-op: ${response.errorBody()?.string()}")))
                }
            }
            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }
}
