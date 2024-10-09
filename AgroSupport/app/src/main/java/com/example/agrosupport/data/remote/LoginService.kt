package com.example.agrosupport.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("authentication/sign-in")
    fun signIn(@Body request: LoginRequestDto): Call<LoginResponseDto>
}
