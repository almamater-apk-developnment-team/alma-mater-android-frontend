package com.example.journalia_admin_cms

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.concurrent.TimeUnit

val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)  // Increase timeout duration
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

private val retrofit=Retrofit.Builder()
    .baseUrl("https://brucewaynegotham007.pythonanywhere.com")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val FileUploadClient= retrofit.create(FileUpload::class.java)
val LoginClient = retrofit.create(Login::class.java)
val SecretClient = retrofit.create(Secret::class.java)

interface FileUpload{
    @Multipart
    @POST("/upload/")
    suspend fun uploadFile(@Part file: MultipartBody.Part): Response<UploadResponse>
    @POST("/detailsUpload/")
    suspend fun detailsUpload(@Body details:adminDashBoardInfo)

}

interface Login {
    @POST("/login")
    suspend fun login(@Body request: LoginBody): LoginResponse
}

interface Secret {
    @POST("/check-secret")
    suspend fun secret(@Body request: SecretBody): SecretResponse
}
data class UploadResponse(
    val message: String,
    val url: String
)