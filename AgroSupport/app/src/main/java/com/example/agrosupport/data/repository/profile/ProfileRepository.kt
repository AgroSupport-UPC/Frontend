package com.example.agrosupport.data.repository.profile

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.agrosupport.common.Resource
import com.example.agrosupport.data.remote.profile.ProfileService
import com.example.agrosupport.data.remote.profile.toProfile
import com.example.agrosupport.domain.profile.CreateProfile
import com.example.agrosupport.domain.profile.Profile
import com.example.agrosupport.domain.profile.UpdateProfile

class ProfileRepository(private val profileService: ProfileService) {
    suspend fun createProfileFarmer(token: String, profile: CreateProfile): Resource<Profile> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = profileService.createProfile(
            bearerToken,
            CreateProfile(
                profile.userId,
                profile.firstName,
                profile.lastName,
                profile.city,
                profile.country,
                profile.birthDate,
                profile.description,
                "",
                profile.photo,
                0
            )
        )
        if (response.isSuccessful) {
            response.body()?.let { profileDto ->
                val profileCreated = profileDto.toProfile()
                return@withContext Resource.Success(profileCreated)
            }
            return@withContext Resource.Error(message = "No se pudo crear el perfil para granjero")
        }
        return@withContext Resource.Error(response.message())
    }

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

    suspend fun updateProfile(userId: Long, token: String, profile: UpdateProfile): Resource<Profile> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = profileService.updateProfile(userId, bearerToken, profile)
        if (response.isSuccessful) {
            response.body()?.let { profileDto ->
                val updatedProfile = profileDto.toProfile()
                return@withContext Resource.Success(data = updatedProfile)
            }
            return@withContext Resource.Error(message = "No se pudo actualizar el perfil")
        }
        return@withContext Resource.Error(response.message())
    }



}