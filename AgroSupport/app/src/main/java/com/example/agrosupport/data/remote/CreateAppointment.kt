package com.example.agrosupport.data.remote

data class CreateAppointment(
    val advisorId: Long,
    val farmerId: Long,
    val message: String,
    val status: String,
    val scheduledDate: String,
    val startTime: String,
    val endTime: String
)