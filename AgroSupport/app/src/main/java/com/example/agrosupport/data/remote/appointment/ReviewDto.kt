package com.example.agrosupport.data.remote.appointment

import com.example.agrosupport.domain.appointment.Review

data class ReviewDto(
    val id: Long,
    val advisorId: Long,
    val farmerId: Long,
    val comment: String,
    val rating: Int
)

fun ReviewDto.toReview() = Review(id, advisorId, farmerId, comment, rating)