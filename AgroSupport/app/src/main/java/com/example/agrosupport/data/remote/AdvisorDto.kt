package com.example.agrosupport.data.remote

import com.example.agrosupport.domain.Advisor

data class AdvisorDto(
    val id: Long,
    val userId: Long,
    val rating: Double,
)

fun AdvisorDto.toAdvisor() = Advisor(id, userId, rating)