package com.kobietka.social_fitness_app.network.repository

import com.google.gson.Gson
import com.kobietka.social_fitness_app.domain.repository.remote.PostRemoteRepository
import com.kobietka.social_fitness_app.domain.service.PostService
import com.kobietka.social_fitness_app.network.request.CreatePostRequest
import com.kobietka.social_fitness_app.network.request.EditPostRequest
import com.kobietka.social_fitness_app.network.response.InvalidFieldErrorResponse
import com.kobietka.social_fitness_app.network.response.PostDto
import com.kobietka.social_fitness_app.util.Result
import retrofit2.HttpException
import java.io.IOException


class PostRemoteRepositoryImpl(private val postService: PostService) : PostRemoteRepository {
    override suspend fun getPost(id: String): Result<PostDto> {
        return try {
            val response = postService.getPost(id = id)
            Result.Success(data = response)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> Result.Unauthorized()
                404 -> Result.Failure(message = "Post not found.")
                else -> {
                    Result.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }

    override suspend fun createPost(createPostRequest: CreatePostRequest): Result<PostDto> {
        return try {
            val response = postService.createPost(createPostRequest = createPostRequest)
            Result.Success(data = response)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> Result.Unauthorized()
                422 -> {
                    val responseString = exception.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(responseString, InvalidFieldErrorResponse::class.java)
                    val message = errorResponse.violations.foldRight(initial = ""){ violation, acc ->
                        acc + violation.message + "\n"
                    }
                    Result.Failure(message = message)
                }
                else -> {
                    Result.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }

    override suspend fun editPost(id: String, editPostRequest: EditPostRequest): Result<PostDto> {
        return try {
            val response = postService.editPost(
                id = id,
                editPostRequest = editPostRequest
            )
            Result.Success(data = response)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> Result.Unauthorized()
                404 -> Result.Failure(message = "Post not found.")
                422 -> {
                    val responseString = exception.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(responseString, InvalidFieldErrorResponse::class.java)
                    val message = errorResponse.violations.foldRight(initial = ""){ violation, acc ->
                        acc + violation.message + "\n"
                    }
                    Result.Failure(message = message)
                }
                else -> {
                    Result.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }

    override suspend fun deletePost(id: String): Result<Boolean> {
        return try {
            postService.deletePost(id = id)
            Result.Success(data = true)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> Result.Unauthorized()
                404 -> Result.Failure(message = "Post not found.")
                else -> {
                    Result.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }
}