package com.example.agrosupport.data.remote.appointment

import com.example.agrosupport.domain.appointment.AvailableDate

data class AvailableDateDto(
    val id: Long,
    val advisorId: Long,
    val availableDate: String,
    val startTime: String,
    val endTime: String,
)

fun AvailableDateDto.toAvailableDate() = AvailableDate(id, advisorId, availableDate, startTime, endTime)