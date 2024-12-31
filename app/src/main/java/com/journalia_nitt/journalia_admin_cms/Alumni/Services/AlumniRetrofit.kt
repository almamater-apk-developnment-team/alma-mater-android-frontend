package com.journalia_nitt

import com.example.journalia.Student.Responses.GeneralResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*


// Define APIs
interface AlumniAccountApi {
    @POST("/alumni/create-account/")
    suspend fun createAccount(@Body account: AlumniAccount): Response<GeneralResponse>

    @POST("/alumni/login/")
    suspend fun login(@Body request: LoginBody): Response<LoginResponse>
}

//interface AlumniUploadApi {
//    @POST("/alumni/upload/")
//    suspend fun uploadContent(@Body upload: AlumniUpload): Response<UploadResponse>
//
//    @GET("/alumni/uploads/")
//    suspend fun fetchAllUploads(): Response<FetchUploadsResponse>
//
//    @GET("/alumni/uploads/{username}")
//    suspend fun fetchUploadsByUsername(@Path("username") username: String): Response<FetchUploadsResponse>
//
//    @POST("/alumni/upload/{id}/comment/")
//    suspend fun addComment(@Path("id") id: String, @Body comment: Comment): Response<CommentResponse>
//
//    @POST("/alumni/upload/{id}/upvote/")
//    suspend fun upvotePost(@Path("id") id: String, @Body upvote: Upvote): Response<GeneralResponse>
//
//    @DELETE("/alumni/upload/{id}/delete/")
//    suspend fun deletePost(@Path("id") id: String): Response<GeneralResponse>
//
//    @POST("/alumni/upload/{id}/recreate/")
//    suspend fun recreatePost(@Path("id") id: String, @Body upload: AlumniUpload): Response<UploadResponse>
//}

// Data classes
//data class GeneralResponse(val message: String)
data class LoginResponse(val message: String, val logged_account: LoggedInAccount)

data class LoggedInAccount(
    val username: String,
    val designation: String,
    val email: String
)
data class AlumniAccount(
    val username: String,
    val designation: String,
    val email: String,
    val password: String,
    val roll_number: String // camelCase convention
)

//data class UploadResponse(val message: String, val data: AlumniUpload)
//data class FetchUploadsResponse(val message: String, val uploads: List<UploadOutput>)
//data class CommentResponse(val message: String, val comment: Comment)

data class LoginBody(val email: String, val password: String)
//data class Upvote(val username: String, val id: String)

// File upload API
interface FileUploadApi {
    @Multipart
    @POST("/upload/")
    suspend fun uploadFile(@Part file: MultipartBody.Part): Response<FileUploadResponse>
}

// File upload response data class
data class FileUploadResponse(val message: String, val url: String)

// Example of initializing the file upload API
//val fileUploadApi: FileUploadApi = retrofit.create(FileUploadApi::class.java)

