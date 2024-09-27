package com.example.agrosupport.data.remote

import com.example.agrosupport.domain.Farmer

data class FarmerDto(
    val id: Long,
    val userId: Long
)

fun FarmerDto.toFarmer() = Farmer(id, userId)