package com.example.agrosupport.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ReviewService {
    @GET("reviews")
    suspend fun getReviews(@Header("Authorization") token: String): Response<List<ReviewDto>>
    @GET("reviews/{advisorId}/advisor")
    suspend fun getReviewsByAdvisor(@Path("advisorId") advisorId: Long, @Header("Authorization") token: String): Response<List<ReviewDto>>

}