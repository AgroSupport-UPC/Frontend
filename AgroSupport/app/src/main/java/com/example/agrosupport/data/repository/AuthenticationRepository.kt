package com.example.agrosupport.data.repository

import com.example.agrosupport.common.Resource
import com.example.agrosupport.data.local.UserDao
import com.example.agrosupport.data.local.UserEntity
import com.example.agrosupport.data.remote.AuthenticationService
import com.example.agrosupport.domain.AuthenticationRequest
import com.example.agrosupport.domain.AuthenticationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationRepository(private val authenticationService: AuthenticationService, private val userDao: UserDao) {
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

    suspend fun insertUser(auth: AuthenticationResponse) = withContext(Dispatchers.IO) {
        userDao.insert(UserEntity(auth.id, auth.token))
    }

    suspend fun deleteUser(auth: AuthenticationResponse) = withContext(Dispatchers.IO) {
        userDao.delete(UserEntity(auth.id, auth.token))
    }

    suspend fun getUser(): Resource<UserEntity> = withContext(Dispatchers.IO) {
        val users = userDao.getAll()
        if (users.isNotEmpty()) {
            return@withContext Resource.Success(users[0])
        }
        return@withContext Resource.Error(message = "No se encontró usuario")
    }
}