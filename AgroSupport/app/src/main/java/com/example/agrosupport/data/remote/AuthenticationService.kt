package com.example.agrosupport.data.remote

import com.example.agrosupport.domain.AuthenticationRequest
import com.example.agrosupport.domain.AuthenticationResponse
import com.example.agrosupport.domain.SignUpRequest
import com.example.agrosupport.domain.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("authentication/sign-up")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("authentication/sign-in")
    suspend fun signIn(@Body request: AuthenticationRequest): Response<AuthenticationResponse>
}
