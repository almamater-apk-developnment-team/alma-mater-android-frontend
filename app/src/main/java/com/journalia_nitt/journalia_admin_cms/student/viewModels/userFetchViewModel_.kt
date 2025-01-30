package com.journalia_nitt.journalia_admin_cms.student.viewModels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journalia_nitt.journalia_admin_cms.student.responses.AlumniUpload
import com.journalia_nitt.journalia_admin_cms.student.responses.Comment
import com.journalia_nitt.journalia_admin_cms.student.responses.CommentResponse
import com.journalia_nitt.journalia_admin_cms.student.responses.Data
import com.journalia_nitt.journalia_admin_cms.student.responses.FetchUploadsResponse
import com.journalia_nitt.journalia_admin_cms.student.responses.GeneralResponse
import com.journalia_nitt.journalia_admin_cms.student.responses.UploadResponse
import com.journalia_nitt.journalia_admin_cms.student.responses.Upvote
import com.journalia_nitt.journalia_admin_cms.student.responses.UserFetch
import com.journalia_nitt.journalia_admin_cms.student.responses.UserFetch1
import com.journalia_nitt.journalia_admin_cms.student.responses.UserFetchClass
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
class FetchViewModel : ViewModel() {
    private val _posts = mutableStateOf(fetchUser())
    val posts: State<fetchUser>  = _posts

    private val _posts1 = mutableStateOf(fetchAllClass())
    val posts1: State<fetchAllClass>  = _posts1

    fun fetchUser(token: String) {
        viewModelScope.launch {
            try {
                val response = uploadClient.fetchUser(token)
                _posts.value = _posts.value.copy(
                    data = response.data,
                    message = response.message
                ) // Update the state with fetched data
                Log.d("successFileFetchFromUser", response.message)
            } catch (e: Exception) {
                Log.d("errorFileFetchFromUser", e.message.toString())
            }
        }
    }
    fun fetchAll() {
        viewModelScope.launch {
            try {
                val response = uploadClient.fetchAll()
                _posts1.value = _posts1.value.copy(
                    data = response.data,
                    message = response.message
                ) // Update the state with fetched data
                Log.d("successFileFetchFromUser", response.message)
            } catch (e: Exception) {
                Log.d("errorFileFetchFromUser", e.message.toString())
            }
        }
    }
}
data class fetchUser(
    val message: String = "",
    val data: Data = Data()
)

data class fetchAllClass(
    val message:String="",
    val data:List<UserFetchClass> = emptyList()
)