package com.example.journalia.Student.Services

import com.example.journalia.Student.Responses.DeadlineRequest
import com.example.journalia.Student.Responses.LoginRequest
import com.example.journalia.Student.Responses.TokenResponse
import com.example.journalia.Student.Responses.ZResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface StudentApiService {
    @POST("/access_token")
    fun getToken(@Body loginRequest: LoginRequest): Call<TokenResponse>
    @POST("/post_deadline")
    fun postDeadline(
        @Header("authorization") token: String,
        @Body deadlineRequest: DeadlineRequest
    ): Call<ZResponse>
}



