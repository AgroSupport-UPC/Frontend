package com.example.agrosupport.presentation

data class AdvisorAppointmentCard(
    val id: Long,
    val advisorName: String,
    val advisorPhoto: String,
    val message: String,
    val status: String,
    val scheduledDate: String,
    val startTime: String,
    val endTime: String,
    val meetingUrl: String
)