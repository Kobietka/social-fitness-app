package com.kobietka.social_fitness_app.network.repository

import com.google.gson.Gson
import com.kobietka.social_fitness_app.domain.repository.remote.CommentRemoteRepository
import com.kobietka.social_fitness_app.domain.service.CommentService
import com.kobietka.social_fitness_app.network.request.CreateCommentRequest
import com.kobietka.social_fitness_app.network.request.EditCommentRequest
import com.kobietka.social_fitness_app.network.response.CommentDto
import com.kobietka.social_fitness_app.network.response.InvalidFieldErrorResponse
import com.kobietka.social_fitness_app.util.NetworkResult
import retrofit2.HttpException
import java.io.IOException


class CommentRemoteRepositoryImpl(private val commentService: CommentService) : CommentRemoteRepository {
    override suspend fun createComment(createCommentRequest: CreateCommentRequest): NetworkResult<CommentDto> {
        return try {
            val response = commentService.createComment(createCommentRequest = createCommentRequest)
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
                else -> NetworkResult.Failure(message = "Something went wrong. Try again later.")
            }
        }
    }

    override suspend fun deleteComment(commentId: String): NetworkResult<Boolean> {
        return try {
            commentService.deleteComment(commentId = commentId)
            NetworkResult.Success(data = true)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                404 -> NetworkResult.Failure(message = "Comment not found")
                else -> NetworkResult.Failure(message = "Something went wrong. Try again later.")
            }
        }
    }

    override suspend fun editComment(
        commentId: String,
        editCommentRequest: EditCommentRequest
    ): NetworkResult<CommentDto> {
        return try {
            val response = commentService.editComment(
                commentId = commentId,
                editCommentRequest = editCommentRequest
            )
            NetworkResult.Success(data = response)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                404 -> NetworkResult.Failure(message = "Comment not found")
                422 -> {
                    val responseString = exception.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(responseString, InvalidFieldErrorResponse::class.java)
                    val message = errorResponse.violations.foldRight(initial = ""){ violation, acc ->
                        acc + violation.message + "\n"
                    }
                    NetworkResult.Failure(message = message)
                }
                else -> NetworkResult.Failure(message = "Something went wrong. Try again later.")
            }
        }
    }
}


















