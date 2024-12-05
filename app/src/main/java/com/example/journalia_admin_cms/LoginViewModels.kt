package com.example.journalia_admin_cms

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    suspend fun login(email: String, password: String): Boolean {
        val response = LoginClient.login(
            LoginBody(email = email, password = password)
        )
        return response.isSuccessful
    }
}

class SecretViewModel : ViewModel() {
    suspend fun checkSecret(email: String, secret: String): String? {
        val response = SecretClient.secret(
            SecretBody(
                email = email,
                secret = secret
            )
        )
        return response.token
    }
}
