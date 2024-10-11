package com.example.agrosupport.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.agrosupport.common.Resource
import com.example.agrosupport.data.remote.AppointmentService
import com.example.agrosupport.domain.CreateAppointment
import com.example.agrosupport.data.remote.toAppointment
import com.example.agrosupport.domain.Appointment

class AppointmentRepository(private val appointmentService: AppointmentService) {
    suspend fun getAppointmentById(id: Long, token: String): Resource<Appointment> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = appointmentService.getAppointment(id, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { appointmentDto ->
                val appointment = appointmentDto.toAppointment()
                return@withContext Resource.Success(appointment)
            }
            return@withContext Resource.Error(message = "Cita no encontrada")
        }
        return@withContext Resource.Error(response.message())
    }

    suspend fun getAllAppointments(token: String): Resource<List<Appointment>> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = appointmentService.getAllAppointments(bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { appointmentList ->
                val appointments = appointmentList.map { it.toAppointment() }
                return@withContext Resource.Success(appointments)
            }
            return@withContext Resource.Error(message = "No se encontraron citas")
        }
        return@withContext Resource.Error(response.message())
    }

    suspend fun getAppointmentsByFarmer(farmerId: Long, token: String): Resource<List<Appointment>> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = appointmentService.getAppointmentsByFarmer(farmerId, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { appointmentList ->
                val appointments = appointmentList.map { it.toAppointment() }
                return@withContext Resource.Success(appointments)
            }
            return@withContext Resource.Error(message = "No se encontraron citas")
        }
        return@withContext Resource.Error(response.message())
    }

    suspend fun getAppointmentsByAdvisorAndFarmer(advisorId: Long, farmerId: Long, token: String): Resource<List<Appointment>> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = appointmentService.getAppointmentsByAdvisorAndFarmer(advisorId, farmerId, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { appointmentList ->
                val appointments = appointmentList.map { it.toAppointment() }
                return@withContext Resource.Success(appointments)
            }
            return@withContext Resource.Error(message = "No se encontraron citas")
        }
        return@withContext Resource.Error(response.message())
    }

    suspend fun createAppointment(token: String, appointment: Appointment): Resource<Appointment> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = appointmentService.createAppointment(bearerToken,
            CreateAppointment(appointment.advisorId, appointment.farmerId, appointment.message, "PENDING", appointment.scheduledDate, appointment.startTime, appointment.endTime) )
        if (response.isSuccessful) {
            response.body()?.let { appointmentDto ->
                val appointmentCreated = appointmentDto.toAppointment()
                return@withContext Resource.Success(appointmentCreated)
            }
            return@withContext Resource.Error(message = "No se pudo crear la cita")
        }
        return@withContext Resource.Error(response.message())
    }
}