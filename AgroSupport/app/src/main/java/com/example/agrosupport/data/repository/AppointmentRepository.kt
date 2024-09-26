package com.example.agrosupport.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.agrosupport.common.Resource
import com.example.agrosupport.data.remote.AppointmentService
import com.example.agrosupport.data.remote.toAppointment
import com.example.agrosupport.domain.Appointment

class AppointmentRepository(private val appointmentService: AppointmentService) {
    suspend fun getAppointmentById(id: Long, token: String): Resource<Appointment> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Token is required")
        }
        val bearerToken = "Bearer $token"
        val response = appointmentService.getAppointment(id, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { appointmentDto ->
                val appointment = appointmentDto.toAppointment()
                return@withContext Resource.Success(appointment)
            }
            return@withContext Resource.Error(message = "Appointment not found")
        }
        return@withContext Resource.Error(response.message())
    }

    suspend fun getAllAppointments(token: String): Resource<List<Appointment>> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Token is required")
        }
        val bearerToken = "Bearer $token"
        val response = appointmentService.getAllAppointments(bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { appointmentList ->
                val appointments = appointmentList.map { it.toAppointment() }
                return@withContext Resource.Success(appointments)
            }
            return@withContext Resource.Error(message = "No appointments found")
        }
        return@withContext Resource.Error(response.message())
    }

    suspend fun getAppointmentsByFarmer(farmerId: Long, token: String): Resource<List<Appointment>> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Token is required")
        }
        val bearerToken = "Bearer $token"
        val response = appointmentService.getAppointmentsByFarmer(farmerId, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { appointmentList ->
                val appointments = appointmentList.map { it.toAppointment() }
                return@withContext Resource.Success(appointments)
            }
            return@withContext Resource.Error(message = "No appointments found")
        }
        return@withContext Resource.Error(response.message())
    }

    suspend fun getAppointmentsByAdvisorAndFarmer(advisorId: Long, farmerId: Long, token: String): Resource<List<Appointment>> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Token is required")
        }
        val bearerToken = "Bearer $token"
        val response = appointmentService.getAppointmentsByAdvisorAndFarmer(advisorId, farmerId, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { appointmentList ->
                val appointments = appointmentList.map { it.toAppointment() }
                return@withContext Resource.Success(appointments)
            }
            return@withContext Resource.Error(message = "No appointments found")
        }
        return@withContext Resource.Error(response.message())
    }
}