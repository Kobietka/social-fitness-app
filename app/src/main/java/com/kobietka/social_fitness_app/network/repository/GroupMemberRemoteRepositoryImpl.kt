package com.kobietka.social_fitness_app.network.repository

import com.kobietka.social_fitness_app.domain.repository.remote.GroupMemberRemoteRepository
import com.kobietka.social_fitness_app.domain.service.GroupMemberService
import com.kobietka.social_fitness_app.network.request.JoinGroupRequest
import com.kobietka.social_fitness_app.network.response.MemberDto
import com.kobietka.social_fitness_app.util.NetworkResult
import retrofit2.HttpException
import java.io.IOException


class GroupMemberRemoteRepositoryImpl(private val groupMemberService: GroupMemberService) : GroupMemberRemoteRepository {
    override suspend fun joinGroup(joinGroupRequest: JoinGroupRequest): NetworkResult<MemberDto> {
        return try {
            val response = groupMemberService.joinGroup(joinGroupRequest = joinGroupRequest)
            NetworkResult.Success(data = response)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                422 -> NetworkResult.Failure(message = "Already a group member.")
                404 -> NetworkResult.Failure(message = "Code invalid.")
                else -> NetworkResult.Failure(message = "Something went wrong. Try again later.")
            }
        }
    }

    override suspend fun leaveGroup(id: String): NetworkResult<Boolean> {
        return try {
            groupMemberService.leaveGroup(id = id)
            NetworkResult.Success(data = true)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                404 -> NetworkResult.Failure(message = "Not a member.")
                else -> NetworkResult.Failure(message = "Something went wrong. Try again later.")
            }
        }
    }
}




















