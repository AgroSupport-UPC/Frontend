package com.example.agrosupport.domain

data class Post(
    val id: Long,
    val advisorId: Long,
    val title: String,
    val description: String,
    val image: String
)