package com.example.journalia_admin_cms

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.InputStream
import androidx.compose.runtime.State

class FileUploadViewModel : ViewModel() {
    val uploadStatus = mutableStateOf("")
    val cloudinaryUrl = mutableStateOf("")
    private val _isLoaded = mutableStateOf(false)
    val isLoaded: State<Boolean>  = _isLoaded


    fun uploadFile(fileUri: Uri?, contentResolver: ContentResolver?) {
        viewModelScope.launch {
            try {
                if (fileUri == null || contentResolver == null) {
                    Log.e("FileUpload", "Invalid URI or ContentResolver")
                    return@launch
                }

                val inputStream: InputStream = contentResolver.openInputStream(fileUri)
                    ?: throw Exception("Unable to open input stream for the file")

                val tempFile = File.createTempFile("upload", null)
                tempFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }

                val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), tempFile)
                val multipartBody = MultipartBody.Part.createFormData("file", tempFile.name, requestBody)

                val response = FileUploadClient.uploadFile(multipartBody).body()

                if (response != null) {
                     cloudinaryUrl.value=response.url
                    Log.d("FileUpload", "Upload successful: $response")
                } else {
                    Log.e("FileUpload", "Upload failed: Response body is null")
                }

                uploadStatus.value = "success"
                tempFile.delete()
                _isLoaded.value=true
            } catch (e: Exception) {
                uploadStatus.value = "failure"
                Log.e("FileUpload", "Error uploading file", e)
            }
        }
    }
    fun uploadDetailsDeadline(details: adminDashBoardInfo){
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
