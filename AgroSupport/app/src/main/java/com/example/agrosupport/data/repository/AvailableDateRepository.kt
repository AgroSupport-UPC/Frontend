package com.example.agrosupport.data.repository

import com.example.agrosupport.common.Resource
import com.example.agrosupport.data.remote.AvailableDateService
import com.example.agrosupport.data.remote.toAvailableDate
import com.example.agrosupport.domain.AvailableDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AvailableDateRepository(private val availableDateService: AvailableDateService) {
    suspend fun getAvailableDatesByAdvisor(advisorId: Long, token: String): Resource<List<AvailableDate>> = withContext(
        Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = availableDateService.getAvailableDates(bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { availableDateDtos ->
                val availableDates = availableDateDtos.map { it.toAvailableDate() }
                val filteredAvailableDates = availableDates.filter { it.advisorId == advisorId }
                return@withContext Resource.Success(data = filteredAvailableDates)
            }
            return@withContext Resource.Error(message = "Error al obtener fechas disponibles")
        }
        return@withContext Resource.Error(response.message())


    }
}