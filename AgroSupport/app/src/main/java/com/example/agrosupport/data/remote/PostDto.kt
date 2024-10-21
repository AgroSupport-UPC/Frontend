package com.example.agrosupport.data.remote

import com.example.agrosupport.domain.Post

data class PostDto(
    val id: Long,
    val advisorId: Long,
    val title: String,
    val description: String,
    val image: String
)

fun PostDto.toPost() = Post(id, advisorId, title, description, image)