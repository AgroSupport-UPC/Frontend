package com.example.agrosupport.domain.profile

data class UpdateProfile(
    val firstName: String,
    val lastName: String,
    val city: String,
    val country: String,
    val birthDate: String,
    val description: String,
    val photo: String,
    val occupation: String,
    val experience: Int
)
