package com.example.agrosupport.data.repository

import com.example.agrosupport.common.Resource
import com.example.agrosupport.data.remote.FarmerService
import com.example.agrosupport.data.remote.toFarmer
import com.example.agrosupport.domain.Farmer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FarmerRepository(private val farmerService: FarmerService) {
    suspend fun searchFarmerByUserId(userId: Long, token: String): Resource<Farmer> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Token is required")
        }
        val bearerToken = "Bearer $token"
        val response = farmerService.getFarmerByUserId(userId, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { farmerDto ->
                val farmer = farmerDto.toFarmer()
                return@withContext Resource.Success(farmer)
            }
            return@withContext Resource.Error(message = "Farmer not found")
        }
        return@withContext Resource.Error(response.message())
    }

    suspend fun searchFarmerByFarmerId(farmerId: Long, token: String): Resource<Farmer> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Token is required")
        }
        val bearerToken = "Bearer $token"
        val response = farmerService.getFarmer(farmerId, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { farmerDto ->
                val farmer = farmerDto.toFarmer()
                return@withContext Resource.Success(farmer)
            }
            return@withContext Resource.Error(message = "Farmer not found")
        }
        return@withContext Resource.Error(response.message())
    }
}