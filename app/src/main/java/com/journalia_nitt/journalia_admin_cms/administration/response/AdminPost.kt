package com.journalia_nitt.journalia_admin_cms.administration.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminPost(
    var postId: String = ""  ,
    var author: String = "" ,
    var title: String = "",
    var description: String = "" ,
    var deadline: Date = Date(0, "",0, 0) ,
    var fileUrl: String? = "" ,
    var type: String = "" ,
    var link1: String = "" ,
    var link2: String = "" ,
    var applicability: String = "" ,
    var time: String = "" ,
    var date: Date = Date(0, "",0, 0)
) : Parcelable

@Parcelize
data class Date(
    val date: Int,
    val monthInString: String,
    val monthInInt: Int,
    val year: Int,
) : Parcelable

data class UploadResponse(
    val message: String,
    val url: String
)
data class FetchResponse(
    val message: String,
    val data: List<ForEachUser>
)
data class ForEachUser(
    val id: String,
    val details: List<AdminPost>
)
data class DeleteResponse(
    val message: String,
    val postId: String
)

