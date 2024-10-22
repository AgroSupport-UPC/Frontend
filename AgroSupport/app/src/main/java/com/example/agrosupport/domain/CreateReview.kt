package com.example.agrosupport.domain

data class CreateReview(
    val advisorId: Long,
    val farmerId: Long,
    val comment: String,
    val rating: Int
)