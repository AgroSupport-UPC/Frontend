package com.example.agrosupport.data.repository

import com.example.agrosupport.common.Resource
import com.example.agrosupport.data.remote.NotificationService
import com.example.agrosupport.data.remote.toNotification
import com.example.agrosupport.domain.Notification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationRepository(private val notificationService: NotificationService) {

    suspend fun getNotifications(userId: Long, token: String): Resource<List<Notification>> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = notificationService.getNotifications(userId, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { notificationsDto ->
                val notifications = notificationsDto.map { it.toNotification() }
                return@withContext Resource.Success(notifications)
            }
            return@withContext Resource.Error(message = "Error al obtener notificaciones")
        }
        else {
            return@withContext Resource.Error(response.message())
        }
    }

    suspend fun deleteNotification(notificationId: Long, token: String): Resource<Unit> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = notificationService.deleteNotification(notificationId, bearerToken)
        if (response.isSuccessful) {
            return@withContext Resource.Success(Unit)
        }
        else {
            return@withContext Resource.Error(response.message())
        }
    }

}