package com.example.agrosupport.domain

data class SignUpRequest(
    val username: String,
    val password: String,
    val roles: List<String>
)