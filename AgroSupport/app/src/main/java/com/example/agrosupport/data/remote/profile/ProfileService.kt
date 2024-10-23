package com.example.agrosupport.data.remote.profile

import com.example.agrosupport.domain.profile.CreateProfile
import com.example.agrosupport.domain.profile.UpdateProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileService {
    @POST("profiles")
    suspend fun createProfile(@Header("Authorization") token: String, @Body profile: CreateProfile): Response<ProfileDto>

    @GET("profiles/{userId}/user")
    suspend fun getProfile(@Path("userId") userId: Long, @Header("Authorization") token: String): Response<ProfileDto>

    @GET("profiles/advisors")
    suspend fun getAdvisors(@Header("Authorization") token: String): Response<List<ProfileDto>>

    @PUT("profiles/{userId}")
    suspend fun updateProfile(@Path("userId") userId: Long, @Header("Authorization") token: String, @Body profile: UpdateProfile): Response<ProfileDto>
}