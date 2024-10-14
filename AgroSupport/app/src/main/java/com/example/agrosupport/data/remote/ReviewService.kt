package com.example.agrosupport.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {
    @GET("reviews")
    suspend fun getReviews(@Header("Authorization") token: String): Response<List<ReviewDto>>
    @GET("reviews")
    suspend fun getReviewsByAdvisor(@Query("advisorId") advisorId: Long, @Header("Authorization") token: String): Response<List<ReviewDto>>

}