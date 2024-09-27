package com.example.agrosupport.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ProfileService {
    @GET("profiles/{userId}/user")
    suspend fun getProfile(@Path("userId") userId: Long, @Header("Authorization") token: String): Response<ProfileDto>
    @GET("profiles/advisors")
    suspend fun getAdvisors(@Header("Authorization") token: String): Response<List<ProfileDto>>
}