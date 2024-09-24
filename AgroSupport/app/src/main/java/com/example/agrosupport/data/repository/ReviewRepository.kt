package com.example.agrosupport.data.repository

import com.example.agrosupport.common.Resource
import com.example.agrosupport.data.remote.ReviewService
import com.example.agrosupport.data.remote.toReview
import com.example.agrosupport.domain.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewRepository(private val reviewService: ReviewService) {
    suspend fun getAdvisorReviewsList(advisorId: Long, token: String): Resource<List<Review>> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Token is required")
        }
        val bearerToken = "Bearer $token"
        val response = reviewService.getReviewsByAdvisor(advisorId, bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { reviewDtos ->
                val reviews = reviewDtos.map { it.toReview() }
                return@withContext Resource.Success(data = reviews)
            }
            return@withContext Resource.Error(message = "Error while getting advisor reviews")
        }
        return@withContext Resource.Error(response.message())
    }
}