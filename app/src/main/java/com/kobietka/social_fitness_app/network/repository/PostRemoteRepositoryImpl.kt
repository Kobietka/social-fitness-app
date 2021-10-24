package com.kobietka.social_fitness_app.network.repository

import com.google.gson.Gson
import com.kobietka.social_fitness_app.domain.repository.remote.PostRemoteRepository
import com.kobietka.social_fitness_app.domain.service.PostService
import com.kobietka.social_fitness_app.network.request.CreatePostRequest
import com.kobietka.social_fitness_app.network.request.EditPostRequest
import com.kobietka.social_fitness_app.network.response.InvalidFieldErrorResponse
import com.kobietka.social_fitness_app.network.response.PostDto
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Result
import retrofit2.HttpException
import java.io.IOException


class PostRemoteRepositoryImpl(private val postService: PostService) : PostRemoteRepository {
    override suspend fun getPost(id: String): NetworkResult<PostDto> {
        return try {
            val response = postService.getPost(id = id)
            NetworkResult.Success(data = response)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                404 -> NetworkResult.Failure(message = "Post not found.")
                else -> {
                    NetworkResult.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }

    override suspend fun createPost(createPostRequest: CreatePostRequest): NetworkResult<PostDto> {
        return try {
            val response = postService.createPost(createPostRequest = createPostRequest)
            NetworkResult.Success(data = response)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                422 -> {
                    val responseString = exception.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(responseString, InvalidFieldErrorResponse::class.java)
                    val message = errorResponse.violations.foldRight(initial = ""){ violation, acc ->
                        acc + violation.message + "\n"
                    }
                    NetworkResult.Failure(message = message)
                }
                else -> {
                    NetworkResult.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }

    override suspend fun editPost(id: String, editPostRequest: EditPostRequest): NetworkResult<PostDto> {
        return try {
            val response = postService.editPost(
                id = id,
                editPostRequest = editPostRequest
            )
            NetworkResult.Success(data = response)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                404 -> NetworkResult.Failure(message = "Post not found.")
                422 -> {
                    val responseString = exception.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(responseString, InvalidFieldErrorResponse::class.java)
                    val message = errorResponse.violations.foldRight(initial = ""){ violation, acc ->
                        acc + violation.message + "\n"
                    }
                    NetworkResult.Failure(message = message)
                }
                else -> {
                    NetworkResult.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }

    override suspend fun deletePost(id: String): NetworkResult<Boolean> {
        return try {
            postService.deletePost(id = id)
            NetworkResult.Success(data = true)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                404 -> NetworkResult.Failure(message = "Post not found.")
                else -> {
                    NetworkResult.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }
}