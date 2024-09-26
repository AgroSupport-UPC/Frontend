package com.example.agrosupport.domain

import com.example.agrosupport.data.remote.AppointmentDto

data class Appointment(
    val id: Long,
    val advisorId: Long,
    val farmerId: Long,
    val message: String,
    val status: String,
    val scheduledDate: String,
    val startTime: String,
    val endTime: String,
    val meetingUrl: String
)
