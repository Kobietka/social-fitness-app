package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.network.request.CreateGroupRequest
import com.kobietka.social_fitness_app.network.response.GetGroupResponse
import com.kobietka.social_fitness_app.util.Resource
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CreateGroupUseCase(private val groupRemoteRepository: GroupRemoteRepository) {
    operator fun invoke(
        name: String,
        description: String
    ): Flow<Resource<GetGroupResponse>> = flow {
        emit(Resource.Loading<GetGroupResponse>())
        val result = groupRemoteRepository.createGroup(
            createGroupRequest = CreateGroupRequest(
                name = name,
                description = description
            )
        )
        when(result){
            is Result.Success -> {
                result.data?.let { response ->
                    emit(Resource.Success<GetGroupResponse>(data = response))
                }
            }
            is Result.Failure -> {
                result.message?.let { message ->
                    emit(Resource.Error<GetGroupResponse>(message = message))
                }
            }
            is Result.Unauthorized -> {
                emit(Resource.Unauthorized<GetGroupResponse>())
            }
        }
    }
}

















