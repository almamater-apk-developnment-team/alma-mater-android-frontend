package com.nitt.administration.viewModels

import androidx.lifecycle.ViewModel
import com.nitt.administration.LoginClient
import com.nitt.administration.SecretClient
import com.nitt.administration.response.LoginBody
import com.nitt.administration.response.SecretBody

class LoginViewModel : ViewModel() {
    suspend fun login(email: String, password: String): Boolean {
        val response = LoginClient.login(
            LoginBody(email = email, password = password)
        )
        return response.isSuccessful
    }
}

class TwoFactorAuthenticationViewModel : ViewModel() {
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
