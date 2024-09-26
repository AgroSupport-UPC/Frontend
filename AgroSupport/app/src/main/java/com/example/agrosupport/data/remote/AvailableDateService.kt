package com.example.agrosupport.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AvailableDateService {
    @GET("available_dates")
    suspend fun getAvailableDates(@Header("Authorization") token: String): Response<List<AvailableDateDto>>
}