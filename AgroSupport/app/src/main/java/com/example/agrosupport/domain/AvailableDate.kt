package com.example.agrosupport.domain

data class AvailableDate (
    val id : Long,
    val advisorId: Long,
    val availableDate: String,
    val startTime: String,
    val endTime: String
)