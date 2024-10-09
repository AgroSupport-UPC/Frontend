package com.example.agrosupport.domain

data class LoginResponse(
    val id: Long,
    val username: String,
    val token: String
)
