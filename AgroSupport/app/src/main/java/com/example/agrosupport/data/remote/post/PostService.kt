package com.example.agrosupport.data.remote.post

import com.example.agrosupport.domain.post.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {
    @POST("posts")
    suspend fun createPost(@Header("Authorization") token: String, @Body post: Post): Response<PostDto>

    @GET("posts")
    suspend fun getPosts(@Header("Authorization") token: String): Response<List<PostDto>>

    @GET("posts")
    suspend fun getPostsByAdvisor(@Header("Authorization") token: String, @Query("advisorId") advisorId: Long): Response<List<PostDto>>

    @GET("posts/{id}")
    suspend fun getPost(@Header("Authorization") token: String, @Path("id") id: Long): Response<PostDto>

}