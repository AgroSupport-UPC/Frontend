package com.example.agrosupport.data.remote

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface NotificationService {
    @GET("notifications/{userId}/user")
    suspend fun getNotifications(@Path("userId") userId: Long, @Header("Authorization") token: String): Response<List<NotificationDto>>
    @GET("notifications/{id}")
    suspend fun getNotification(@Path("id") id: Long, @Header("Authorization") token: String): Response<NotificationDto>
    @DELETE("notifications/{id}")
    suspend fun deleteNotification(@Path("id") id: Long, @Header("Authorization") token: String): Response<Unit>
}