package com.kobietka.social_fitness_app.network.repository

import com.kobietka.social_fitness_app.domain.repository.remote.GroupMemberRemoteRepository
import com.kobietka.social_fitness_app.domain.service.GroupMemberService
import com.kobietka.social_fitness_app.network.request.JoinGroupRequest
import com.kobietka.social_fitness_app.network.response.MemberDto
import com.kobietka.social_fitness_app.util.Result
import retrofit2.HttpException
import java.io.IOException


class GroupMemberRemoteRepositoryImpl(private val groupMemberService: GroupMemberService) : GroupMemberRemoteRepository {
    override suspend fun joinGroup(joinGroupRequest: JoinGroupRequest): Result<MemberDto> {
        return try {
            val response = groupMemberService.joinGroup(joinGroupRequest = joinGroupRequest)
            Result.Success(data = response)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> Result.Unauthorized()
                422 -> Result.Failure(message = "Already a group member.")
                404 -> Result.Failure(message = "Code invalid.")
                else -> {
                    Result.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }

    override suspend fun leaveGroup(id: String): Result<Boolean> {
        return try {
            groupMemberService.leaveGroup(id = id)
            Result.Success(data = true)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> Result.Unauthorized()
                404 -> Result.Failure(message = "Not a member.")
                else -> {
                    Result.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }
}




















