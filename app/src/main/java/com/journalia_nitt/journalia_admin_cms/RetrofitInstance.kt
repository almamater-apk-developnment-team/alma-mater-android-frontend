package com.journalia_nitt.journalia_admin_cms

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.util.concurrent.TimeUnit

val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)  // Increase timeout duration
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

private val retrofit=Retrofit.Builder()
    .baseUrl("https://journaliaadmin.vercel.app/")
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
    suspend fun detailsUpload(@Body details:AdminDashBoardInfo)
    @GET("/fetch-details/")
    suspend fun fetchAdminDetails(): Response<fetchResponse>
    @DELETE("/delete-post/{post_id}")
    suspend fun deletePost(@retrofit2.http.Path("post_id") postId: String): Response<DeleteResponse>
}

interface Login {
    @POST("/login/")
    suspend fun login(@Body request: LoginBody): LoginResponse
}

interface Secret {
    @POST("/check-secret/")
    suspend fun secret(@Body request: SecretBody): SecretResponse
}

data class UploadResponse(
    val message: String,
    val url: String
)

data class fetchResponse(
    val message: String,
    val data: List<forEachUser>
)

data class forEachUser(
    val id: String,
    val details: List<announcement>
)


data class announcement(
    val author: String = "SW Office",
    val title: String = "Hostel Fee Payment",
    val description: String = "",
    val deadline: String = "7 Aug 2025",
    val file_url: String? = "",
    val mode: Int = 0,
    val link1: String = "",
    val link2: String = "",
    val id: String = "" // Add id field
)

data class DeleteResponse(
    val message: String,
    val post_id: String
)
