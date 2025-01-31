package com.nitt.student.viewModels

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nitt.student.responses.UploadResponse
import com.nitt.student.responses.UserUploadClass
import com.nitt.student.responses.userUploadResponse
import com.nitt.student.screens.getFileName
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

private val retrofit = Retrofit.Builder().baseUrl("https://journaliaadmin.vercel.app/").addConverterFactory(GsonConverterFactory.create()).build()
val uploadClient= retrofit.create(handleUpload::class.java)
interface handleUpload{
    @POST("/student/uploadDetails/")
    suspend fun uploadUser(@Body userUploadClass: UserUploadClass): userUploadResponse
    @Multipart
    @POST("/student/upload/")
    suspend fun uploadFile(@Part file: MultipartBody.Part): Response<UploadResponse>

    @GET("/student/fetch{token}")
    suspend fun fetchUser(@Path("token") token: String): UserFetch

    @GET("/student/fetchAll/")
    suspend fun fetchAll(): UserFetch1

    @POST("/student/bookmark/")
    suspend fun bookMark(@Body bookMark: AdminPost,@Header("authorization") token: String): userUploadResponse

    @GET("/student/fetchBookmark/")
    suspend fun getAllBook(@Header("authorization") token: String): BookMarkFetch

    @GET("/student/get-token/")
    suspend fun getStudentToken(@Query("rollno") rollno: String): TokenResponse

    @POST("/student/upvote/")
    suspend fun upvotesPost(@Query("token") token: String, @Query("file_id") file_id: String)

    @POST("/student/addComment/")
    suspend fun addComment(@Query("token") token: String, @Query("post_id") post_id: String,@Body comment: userComments)
}

class UploadViewModel:ViewModel(){
    private val _uploadStatus = mutableStateOf<String?>("")
    val uploadStatus: State<String?>get() = _uploadStatus

    private val _fileUrl = mutableStateOf<String?>("")
    val fileUrl: State<String?>get() = _fileUrl

    fun uploadUser(userUploadClass: UserUploadClass){
        viewModelScope.launch {
            try {
                val response= uploadClient.uploadUser(userUploadClass)

                if(response.file_url=="file_url"){

                    Log.d("successPostUpload",response.message)
                }
            }catch (e:Exception){
                Log.d("errorPostUpload",e.message.toString())
            }
        }
    }

    fun uploadFile(fileUri: Uri?, contentResolver: ContentResolver?) {
        viewModelScope.launch {
            try {
                if (fileUri == null || contentResolver == null) {
                    _uploadStatus.value = "success"
                    Log.e("FileUpload", _uploadStatus.value.toString())

                    return@launch
                }

                // Read file into memory
                val byteArray = contentResolver.openInputStream(fileUri)?.use { it.readBytes() }
                    ?: throw Exception("Unable to read file contents")

                val mimeType = contentResolver.getType(fileUri) ?: "application/octet-stream"
                val requestBody = RequestBody.create(mimeType.toMediaTypeOrNull(), byteArray)

                val multipartBody = MultipartBody.Part.createFormData(
                    "file",
                    getFileName(contentResolver, fileUri),
                    requestBody
                )

                val response = uploadClient.uploadFile(multipartBody)
                if (response.isSuccessful && response.body() != null) {
                    _fileUrl.value = response.body()?.url
                    _uploadStatus.value = "success"
                    Log.d("FileUpload", "Upload successful: ${_fileUrl.value} ${_uploadStatus.value}")

                } else {
                    val errorBody = response.errorBody()?.string()
                    _uploadStatus.value = "failure"
                    Log.e("FileUpload", "Upload failed: ${response.code()}, Error: $errorBody")

                }
            } catch (e: Exception) {
                _uploadStatus.value = "failure"
                Log.e("FileUpload", "Error uploading file", e)
            }
        }
    }
}





