package com.example.agrosupport.data.remote

import com.example.agrosupport.domain.LoginRequest

data class LoginRequestDto(
    val username: String,
    val password: String
)

fun LoginRequestDto.toLoginRequest() = LoginRequest(username, password)