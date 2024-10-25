package com.example.agrosupport.domain.authentication

data class AuthenticationResponse(
    val id: Long,
    val username: String,
    val token: String
)