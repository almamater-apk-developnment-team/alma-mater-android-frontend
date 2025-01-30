package com.journalia_nitt.journalia_admin_cms.student.viewModels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journalia_nitt.journalia_admin_cms.administration.response.AdminPost
import com.journalia_nitt.journalia_admin_cms.student.responses.fetchBookMark
import com.journalia_nitt.journalia_admin_cms.student.sharedPreferences.getTokenDetails
import com.journalia_nitt.journalia_admin_cms.student.sharedPreferences.getUserLoginToken
import kotlinx.coroutines.launch

class bookMarkViewModel: ViewModel(){
    private val _posts = mutableStateOf(fetchBookMark())
    val posts: State<fetchBookMark> = _posts

    fun postBookMark(bookMark: AdminPost ,context: Context){
        viewModelScope.launch {

            val token= "Bearer "+ getTokenDetails(context).toString()
            try {
                val response= uploadClient.bookMark(bookMark,token)
                Log.d("Bookmark test",response.message)
            }catch (
                e:Exception
            ){
                Log.e("Bookmark error",e.message.toString())
            }

        }
    }
    fun fetchBookMark(context: Context) {
        viewModelScope.launch {
            try {
                val token= "Bearer "+ getUserLoginToken(context).toString()
                val response = uploadClient.getAllBook(token)
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