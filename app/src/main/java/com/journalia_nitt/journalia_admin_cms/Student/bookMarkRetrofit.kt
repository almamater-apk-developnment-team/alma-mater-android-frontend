package com.example.journalia.Student

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journalia.Student.Responses.BookMarkFetch
import com.example.journalia.Student.Responses.bookMarkClass
import com.example.journalia.Student.Responses.userUploadResponse
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class BookMark(
    val token: String,
    val author: String,
    val deadline: String,
    val description: String?,
    val file_url: String?,
    val link1: String?,
    val link2: String?,
    val mode: Int,
    val title: String?
)
private val retrofit = Retrofit.Builder().baseUrl("https://dauth-sand.vercel.app/").addConverterFactory(GsonConverterFactory.create()).build()
val bookMarkHandle= retrofit.create(handleBookMark::class.java)

interface handleBookMark{
    @POST("/bookmark/")
    suspend fun bookMark(@Body bookMark: BookMark): userUploadResponse

    @GET("/fetchBookmark/{token}")
    suspend fun getAllBook(@Path("token")token:String): BookMarkFetch

}

class bookMarkViewModel:ViewModel(){
    private val _posts = mutableStateOf(fetchBookMark())
    val posts: State<fetchBookMark>  = _posts

    fun postBookMark(bookMark: BookMark){
        viewModelScope.launch {
            try {
                val response= bookMarkHandle.bookMark(bookMark)
                Log.d("Bookmark test",response.message)
            }catch (
                e:Exception
            ){
                Log.e("Bookmark error",e.message.toString())
            }

        }
    }
    fun fetchBookMark(token: String) {
        viewModelScope.launch {
            try {
                val response = bookMarkHandle.getAllBook(token)
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
}
data class fetchBookMark(
    val message: String = "",
    val data: bookMarkClass = bookMarkClass()
)

