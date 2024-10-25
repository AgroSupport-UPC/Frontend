package com.example.agrosupport.data.repository.advisor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.agrosupport.common.Resource
import com.example.agrosupport.data.remote.advisor.AdvisorService
import com.example.agrosupport.data.remote.advisor.toAdvisor
import com.example.agrosupport.domain.advisor.Advisor

class AdvisorRepository(private val advisorService: AdvisorService) {
    suspend fun searchAdvisorByUserId(userId: Long, token: String): Resource<Advisor> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = advisorService.getAdvisor(userId, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { advisorDto ->
                val advisor = advisorDto.toAdvisor()
                return@withContext Resource.Success(advisor)
            }
            return@withContext Resource.Error(message = "No se encontró asesor")
        }
        return@withContext Resource.Error(response.message())
    }

    suspend fun searchAdvisorByAdvisorId(advisorId: Long, token: String): Resource<Advisor> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requeridod")
        }
        val bearerToken = "Bearer $token"
        val response = advisorService.getAdvisorByAdvisorId(advisorId, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { advisorDto ->
                val advisor = advisorDto.toAdvisor()
                return@withContext Resource.Success(advisor)
            }
            return@withContext Resource.Error(message = "No se encontró asesor")
        }
        return@withContext Resource.Error(response.message())
    }
}