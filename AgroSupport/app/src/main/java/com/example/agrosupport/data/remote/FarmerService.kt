package com.example.agrosupport.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface FarmerService {
    @GET("farmers/{userId}/user")
    suspend fun getFarmer(@Path("userId") userId: Long, @Header("Authorization") token: String): Response<FarmerDto>
}