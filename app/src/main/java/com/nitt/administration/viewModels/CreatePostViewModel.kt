package com.nitt.administration.viewModels

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import androidx.compose.runtime.State
import com.nitt.administration.FileUploadClient
import com.nitt.administration.response.AdminPost
import com.nitt.administration.screens.getFileName
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class CreatePostViewModel : ViewModel() {
    private val _uploadStatus = mutableStateOf("")
    val uploadStatus: State<String>  = _uploadStatus
    private val _isLoaded = mutableStateOf(false)
    var isLoaded: MutableState<Boolean> = _isLoaded
    val fileUrl = mutableStateOf<String?>("")
    private val _isDeleted = mutableStateOf(false)
    val isDeleted: State<Boolean> = _isDeleted

    fun deletePost(postId: String) {
        viewModelScope.launch {
            try {
                val response = FileUploadClient.deletePost(postId)
                if (response.isSuccessful) {
                    _isDeleted.value = true
                    Log.d("FileDelete", "Post deleted successfully: ${response.body()?.message}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    _isDeleted.value = false
                    Log.e("FileDelete", "Delete failed: ${response.code()}, Error: $errorBody")
                }
            } catch (e: Exception) {
                _isDeleted.value = false
                Log.e("FileDelete", "Error deleting post", e)
            }
        }
    }
    fun uploadFile(fileUri: Uri?, contentResolver: ContentResolver?,details: AdminPost) {
        viewModelScope.launch {
            try {
                _uploadStatus.value = "Uploading"
                if (fileUri == null || contentResolver == null) {
                    Log.e("FileUpload", "Invalid URI or ContentResolver")
                    _uploadStatus.value = "Invalid URI or ContentResolver"
                    return@launch
                }
                val byteArray = contentResolver.openInputStream(fileUri)?.use { it.readBytes() }
                    ?: throw Exception("Unable to read file contents")
                val mimeType = contentResolver.getType(fileUri) ?: "application/octet-stream"
                val requestBody = RequestBody.create(mimeType.toMediaTypeOrNull(), byteArray)
                val multipartBody = MultipartBody.Part.createFormData(
                    "file",
                    getFileName(contentResolver, fileUri),
                    requestBody
                )
                val response = FileUploadClient.uploadFile(multipartBody)
                if (response.isSuccessful && response.body() != null) {
                    fileUrl.value = response.body()?.url
                    Log.d("FileUpload", "Upload successful: ${response.body()?.url}")
                    try{
                        details.fileUrl = fileUrl.value
                        val fileResponse= FileUploadClient.detailsUpload(details)
                        _uploadStatus.value = "success"
                        Log.d("FileUploadDetails", "Upload successful: $fileResponse")
                    }
                    catch (e:Exception){
                        _uploadStatus.value = "failed to upload"
                        Log.e("FileUploadDetails", "Error uploading file", e)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("FileUpload", "Upload failed: ${response.code()}, Error: $errorBody")
                    _uploadStatus.value = "failure"
                }
                _isLoaded.value = true
            } catch (e: Exception) {
                _uploadStatus.value = "failure"
                Log.e("FileUpload", "Error uploading file", e)
            }
        }
    }
}
