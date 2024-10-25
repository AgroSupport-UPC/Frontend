package com.example.agrosupport.data.remote.appointment

import com.example.agrosupport.domain.appointment.Appointment

data class AppointmentDto(
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

fun AppointmentDto.toAppointment() = Appointment(
    id, advisorId, farmerId, message, status, scheduledDate, startTime, endTime, meetingUrl
)