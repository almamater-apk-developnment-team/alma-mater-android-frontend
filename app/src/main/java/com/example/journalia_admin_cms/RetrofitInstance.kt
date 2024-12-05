package com.example.journalia_admin_cms

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Body

private val retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:5000/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val FileUploadClient = retrofit.create(FileUpload::class.java)
val LoginClient = retrofit.create(Login::class.java)
val SecretClient = retrofit.create(Secret::class.java)

interface Login {
    @POST("/login")
    suspend fun login(@Body request: LoginBody): LoginResponse
}

interface Secret {
    @POST("/check-secret")
    suspend fun secret(@Body request: SecretBody): SecretResponse
}

interface FileUpload{
    @Multipart
    @POST("/upload/")
    suspend fun uploadFile(@Part file: MultipartBody.Part): Response<UploadResponse>
}

data class UploadResponse(
    val message: String,
    val url: String
)