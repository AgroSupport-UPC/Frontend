package com.example.agrosupport.data.repository

import com.example.agrosupport.data.remote.LoginService
import com.example.agrosupport.data.remote.LoginRequestDto
import com.example.agrosupport.data.remote.LoginResponseDto
import com.example.agrosupport.data.remote.toLoginResponse
import com.example.agrosupport.domain.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val loginService: LoginService) {

    fun signIn(username: String, password: String, callback: (Result<LoginResponse>) -> Unit) {
        val requestDto = LoginRequestDto(username, password)

        loginService.signIn(requestDto).enqueue(object : Callback<LoginResponseDto> {
            override fun onResponse(call: Call<LoginResponseDto>, response: Response<LoginResponseDto>) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let { responseDto ->
                            // Convertir el LoginResponseDto a LoginResponse
                            val loginResponse = LoginResponse(
                                id = responseDto.id,
                                username = responseDto.username,
                                token = responseDto.token
                            )
                            callback(Result.success(loginResponse))
                        } ?: callback(Result.failure(Exception("Response body is null")))
                    } else {
                        callback(Result.failure(Exception("Error: ${response.code()}")))
                    }
                } catch (e: Exception) {
                    callback(Result.failure(e))
                }
            }

            override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }
}
