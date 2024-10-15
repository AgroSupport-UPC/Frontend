package com.example.agrosupport.data.remote

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AvailableDateService {
    @GET("available_dates")
    suspend fun getAvailableDates(@Header("Authorization") token: String): Response<List<AvailableDateDto>>

    @DELETE("available_dates/{availableDateId}")
    suspend fun deleteAvailableDate(
        @Path("availableDateId") availableDateId: Long,
        @Header("Authorization") token: String): Response<Unit>
}