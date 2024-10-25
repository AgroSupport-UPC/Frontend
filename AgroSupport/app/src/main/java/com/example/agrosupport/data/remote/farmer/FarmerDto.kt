package com.example.agrosupport.data.remote.farmer

import com.example.agrosupport.domain.farmer.Farmer

data class FarmerDto(
    val id: Long,
    val userId: Long
)

fun FarmerDto.toFarmer() = Farmer(id, userId)