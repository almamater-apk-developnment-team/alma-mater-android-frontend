package com.journalia_nitt.journalia_admin_cms.administration.viewModels

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import androidx.compose.runtime.State
import com.journalia_nitt.journalia_admin_cms.administration.FileUploadClient
import com.journalia_nitt.journalia_admin_cms.administration.response.AdminDashBoardInfo
import com.journalia_nitt.journalia_admin_cms.administration.screens.getFileName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class FileUploadViewModel : ViewModel() {

    private val _uploadFileStatus = MutableStateFlow("")
    val uploadFileStatus: StateFlow<String> get() = _uploadFileStatus

    private val _uploadedFileUrl = MutableStateFlow("")
    val uploadedFileUrl: StateFlow<String> get() = _uploadedFileUrl

    private val _isLoaded = mutableStateOf(false)
    val isLoaded: State<Boolean> = _isLoaded

    private val _deleteStatus = MutableStateFlow("")
    val deleteStatus = mutableStateOf("")

    private val _isDeleted = mutableStateOf(false)
    val isDeleted: State<Boolean> = _isDeleted

    fun deletePost(postId: String) {
        viewModelScope.launch {
            try {
                val response = FileUploadClient.deletePost(postId)
                if (response.isSuccessful) {
                    deleteStatus.value = "success"
                    _isDeleted.value = true
                    Log.d("FileDelete", "Post deleted successfully: ${response.body()?.message}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    deleteStatus.value = "failure"
                    _isDeleted.value = false
                    Log.e("FileDelete", "Delete failed: ${response.code()}, Error: $errorBody")
                }
            } catch (e: Exception) {
                deleteStatus.value = "failure"
                _isDeleted.value = false
                Log.e("FileDelete", "Error deleting post", e)
            }
        }
    }

    fun uploadFile(fileUri: Uri?, contentResolver: ContentResolver?) {
        viewModelScope.launch {
            try {
                if (fileUri == null || contentResolver == null) {
                    Log.e("FileUpload", "Invalid URI or ContentResolver")
                    _uploadFileStatus.value = "success1"
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

                val response = FileUploadClient.uploadFile(multipartBody)
                if (response.isSuccessful) {
                    if(response.body() != null) {
                        _uploadedFileUrl.value = response.body()?.url.toString()
                    }
                    Log.d("FileUpload", "Upload successful: ${response.body()?.url}")
                    _uploadFileStatus.value = "success"
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("FileUpload", "Upload failed: ${response.code()}, Error: $errorBody")
                    _uploadFileStatus.value = "failure"
                }

                _isLoaded.value = true
            } catch (e: Exception) {
                _uploadFileStatus.value = "failure"
                Log.e("FileUpload", "Error uploading file", e)
            }
        }
    }

    fun uploadDetailsDeadline(details: AdminDashBoardInfo){
        viewModelScope.launch{
            try{
                val response=FileUploadClient.detailsUpload(details)
                Log.d("FileUploadDetails", "Upload successful: $response")
            }
            catch (e:Exception){
                Log.e("FileUploadDetails", "Error uploading file", e)
            }
        }
    }
}
