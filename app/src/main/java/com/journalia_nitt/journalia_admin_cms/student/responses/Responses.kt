package com.journalia_nitt.journalia_admin_cms.student.responses

import com.google.gson.annotations.SerializedName

data class TokenId(
    @SerializedName("id_token")
    val id:String
)

data class UserInfo(
    val name:String,
    val collegeId: String,
    val loginStatus:Boolean,
    val role:String
)
data class TokenResponse(val token: String)

data class fetchBookMark(
    val message: String = "",
    val data: bookMarkClass = bookMarkClass()
)

data class LoginRequest(val name: String)
data class FileData(
    val name: String,
    val mimeType: String,
    val content: ByteArray
)
data class DeadlineRequest(
    val deadline: String,
)


data class ZResponse(
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("message")
    val message: String,
    )