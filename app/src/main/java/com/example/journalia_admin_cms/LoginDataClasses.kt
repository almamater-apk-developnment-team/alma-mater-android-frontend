package com.example.journalia_admin_cms

data class LoginBody(
    val email: String,
    val password: String
)

data class SecretBody(
    val email: String,
    val secret: String
)

data class LoginResponse(
    val response : String,
)

data class SecretResponse(
    val token: String
)