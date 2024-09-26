package com.example.agrosupport.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.agrosupport.common.Resource
import com.example.agrosupport.data.remote.ProfileService
import com.example.agrosupport.data.remote.toProfile
import com.example.agrosupport.domain.Profile

class ProfileRepository(private val profileService: ProfileService) {
    suspend fun searchProfile(userId: Long, token: String): Resource<Profile> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = profileService.getProfile(userId, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { profileDto ->
                val profile = profileDto.toProfile()
                return@withContext Resource.Success(data = profile)
            }
            return@withContext Resource.Error(message = "No se encontró perfil")
        }
        return@withContext Resource.Error(response.message())
    }

    suspend fun getAdvisorList(token: String): Resource<List<Profile>> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = profileService.getAdvisors(bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { profileDtos ->
                val advisors = profileDtos.map { it.toProfile() }
                return@withContext Resource.Success(data = advisors)
            }
            return@withContext Resource.Error(message = "Error al obtener lista de asesores")
        }
        return@withContext Resource.Error(response.message())
    }
}