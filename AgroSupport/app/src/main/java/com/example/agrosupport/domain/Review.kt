package com.example.agrosupport.domain

data class Review (
    val id: Long,
    val advisorId: Long,
    val farmerId: Long,
    val comment: String,
    val rating: Int
)