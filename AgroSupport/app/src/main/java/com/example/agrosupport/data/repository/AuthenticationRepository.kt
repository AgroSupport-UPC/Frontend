package com.example.agrosupport.data.repository

import com.example.agrosupport.common.Resource
import com.example.agrosupport.data.remote.AuthenticationService
import com.example.agrosupport.domain.AuthenticationRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationRepository(private val authenticationService: AuthenticationService) {
    suspend fun signIn(username: String, password: String) = withContext(Dispatchers.IO) {
        val response = authenticationService.signIn(AuthenticationRequest(username, password))
        if (response.isSuccessful) {
            response.body()?.let { loginResponse ->
                return@withContext Resource.Success(loginResponse)
            }
            return@withContext Resource.Error(message = "Error al iniciar sesión")
        }
        return@withContext Resource.Error(message = response.message().ifEmpty { "Usuario o contraseña incorrecto" })
    }
}