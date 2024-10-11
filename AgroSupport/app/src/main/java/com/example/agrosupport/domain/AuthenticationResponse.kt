package com.example.agrosupport.domain

data class AuthenticationResponse(
    val id: Long,
    val username: String,
    val token: String
)