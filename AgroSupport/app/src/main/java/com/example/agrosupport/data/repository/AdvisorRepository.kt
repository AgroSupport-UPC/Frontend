package com.example.agrosupport.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.agrosupport.common.Resource
import com.example.agrosupport.data.remote.AdvisorService
import com.example.agrosupport.data.remote.toAdvisor
import com.example.agrosupport.domain.Advisor

class AdvisorRepository(private val advisorService: AdvisorService) {
    suspend fun searchAdvisor(userId: Long, token: String): Resource<Advisor> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Token is required")
        }
        val bearerToken = "Bearer $token"
        val response = advisorService.getAdvisor(userId, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { advisorDto ->
                val advisor = advisorDto.toAdvisor()
                return@withContext Resource.Success(advisor)
            }
            return@withContext Resource.Error(message = "Advisor not found")
        }
        return@withContext Resource.Error(response.message())
    }
}