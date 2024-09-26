package com.example.agrosupport.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AppointmentService {
    @GET("appointments/{id}")
    suspend fun getAppointment(@Path("id") id: Long, @Header("Authorization") token: String): Response<AppointmentDto>

    @GET("appointments")
    suspend fun getAllAppointments(@Header("Authorization") token: String): Response<List<AppointmentDto>>

    @GET("appointments/{farmerId}/farmer")
    suspend fun getAppointmentsByFarmer(@Path("farmerId") farmerId: Long, @Header("Authorization") token: String): Response<List<AppointmentDto>>

    @GET("appointments/{advisorId}/advisor/{farmerId}/farmer")
    suspend fun getAppointmentsByAdvisorAndFarmer(@Path("advisorId") advisorId: Long, @Path("farmerId") farmerId: Long, @Header("Authorization") token: String): Response<List<AppointmentDto>>
}
