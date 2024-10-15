package com.example.agrosupport.data.remote

import com.example.agrosupport.domain.Notification

data class NotificationDto(
    val id: Long,
    val userId: Long,
    val title: String,
    val message: String,
    val sendAt: String
)

fun NotificationDto.toNotification() = Notification(id, userId, title, message, sendAt)