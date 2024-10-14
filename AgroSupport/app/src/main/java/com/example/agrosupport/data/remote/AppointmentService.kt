package com.example.agrosupport.data.remote

import com.example.agrosupport.domain.CreateAppointment
import com.example.agrosupport.domain.UpdateAppointment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @POST("appointments")
    suspend fun createAppointment(@Header("Authorization") token: String, @Body appointment: CreateAppointment): Response<AppointmentDto>

    @PUT("appointments/{id}")
    suspend fun updateAppointment(@Path("id") id: Long, @Header("Authorization") token: String, @Body appointment: UpdateAppointment): Response<AppointmentDto>

    @DELETE("appointments/{id}")
    suspend fun deleteAppointment(@Path("id") id: Long, @Header("Authorization") token: String): Response<Unit>
}