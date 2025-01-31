package com.nitt.alumni

import android.content.ContentResolver
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.nitt.alumni.response.AlumniUpload
import com.nitt.alumni.response.LoggedInAccount

var clickedPost = mutableStateOf(
    AlumniUpload(
        username = "",
        title = "",
        description = "",
        id = "",
        file_url = "",
        link1 = "",
        link2 = "",
        comments = mutableListOf(),
        upvotes = 0,
        upvoters = mutableListOf(),
    )
)
val EditState = mutableStateOf(false)
var theUser = LoggedInAccount(
    username = "",
    email = "",
    designation = ""
)
var Uri = mutableStateOf<Uri?>(null)
var ContentResolver1 = mutableStateOf<ContentResolver?>(null)
val gradient = Brush.linearGradient(
    colors = listOf(Color(150, 103, 224), Color(188, 128, 240))
)