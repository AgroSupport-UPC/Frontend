package com.example.agrosupport.data.remote

import com.example.agrosupport.domain.LoginResponse

data class LoginResponseDto(
    val id: Long,
    val username: String,
    val token: String
)

fun LoginResponseDto.toLoginResponse() = LoginResponse(id, username, token)