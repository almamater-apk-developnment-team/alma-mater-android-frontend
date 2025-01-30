package com.nitt.student.viewModels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nitt.administration.response.AdminPost
import com.nitt.student.bookMarkHandle
import com.nitt.student.responses.fetchBookMark
import com.nitt.student.sharedPreferences.getTokenDetails
import kotlinx.coroutines.launch

class BookMarkViewModel: ViewModel(){
    private val _posts = mutableStateOf(fetchBookMark())
    val posts: State<fetchBookMark> = _posts

    fun postBookMark(bookMark: AdminPost, username:String, context: Context){
        viewModelScope.launch {
            val token= "Bearer "+ getTokenDetails(context).toString()
            try {
                val response= bookMarkHandle.bookMark(bookMark,username,token)
                Log.d("Bookmark test",response.message)
            }catch (
                e:Exception
            ){
                Log.e("Bookmark error",e.message.toString())
            }
        }
    }
    fun fetchBookMark(rollNo: String,context: Context) {
        viewModelScope.launch {
            try {
                val token= "Bearer "+ getTokenDetails(context).toString()
                val response = bookMarkHandle.getAllBook(rollNo,token)
                _posts.value = _posts.value.copy(
                    data = response.data,
                    message = response.message
                )
                Log.d("successFileFetchFromUser", response.message)
            } catch (e: Exception) {
                Log.d("errorFileFetchFromUser", e.message.toString())
            }
        }
    }
}