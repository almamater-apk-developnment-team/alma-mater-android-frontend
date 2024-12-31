package com.example.journalia.Student.Responses

import com.google.gson.annotations.SerializedName

data class TokenId(
    @SerializedName("id_token")
    val id:String
)

data class StudentInfo(
    val name:String,
    val collegeId: String,
    val loginStatus:Boolean
)
data class TokenResponse(val token: String)

data class LoginRequest(val name: String)

data class DeadlineRequest(
    val deadline: String,
)

data class ZResponse(
    @SerializedName("deadline")
    val deadline: String,
    @SerializedName("message")
    val message: String,
    )