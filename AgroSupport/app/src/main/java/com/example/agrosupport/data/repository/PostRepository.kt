package com.example.agrosupport.data.repository

import com.example.agrosupport.common.Resource
import com.example.agrosupport.data.remote.PostService
import com.example.agrosupport.data.remote.toPost
import com.example.agrosupport.domain.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepository(private val postService: PostService) {

    suspend fun getPosts(token: String): Resource<List<Post>> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = postService.getPosts(bearerToken)
        if (response.isSuccessful) {
            response.body()?.let { postsDto ->
                val posts = postsDto.map { it.toPost() }
                return@withContext Resource.Success(posts)
            }
            return@withContext Resource.Error(message = "Error al obtener publicaciones")
        }
        else {
            return@withContext Resource.Error(response.message())
        }
    }

    suspend fun createPost(token: String, post: Post): Resource<Post> = withContext(Dispatchers.IO) {
        if (token.isBlank()) {
            return@withContext Resource.Error(message = "Un token es requerido")
        }
        val bearerToken = "Bearer $token"
        val response = postService.createPost(bearerToken, post)
        if (response.isSuccessful) {
            response.body()?.let { postDto ->
                val postCreated = postDto.toPost()
                return@withContext Resource.Success(postCreated)
            }
            return@withContext Resource.Error(message = "No se pudo crear la publicación")
        }
        return@withContext Resource.Error(response.message())
    }
}