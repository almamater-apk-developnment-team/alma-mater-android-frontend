package com.nitt.student.responses

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import okhttp3.MultipartBody

data class Post(
    var postId: String = "",
    var id: String = "",
    var username: String = "",
    var title: String = "",
    var description: String = "",
    var anonymity: String = "enable",
    var category: String = "college",
    var enabled: String = "",
    var time: String = "",
    var date: Date = Date(0, "", 0),
    var likes: Int = 0,
    var canComment: MutableState<Boolean> = mutableStateOf(true),
    var comments: List<Comment> = emptyList(),
    var image: MultipartBody.Part? = null
)
data class Date(
    val date: Int,
    val month: String,
    val year: Int
)
data class Comment(
    val commentId: String,
    val comment: String,
    val username: String,
    val time: String,
    val date: Date
)