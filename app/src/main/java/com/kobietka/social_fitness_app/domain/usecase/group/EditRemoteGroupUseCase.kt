package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.network.request.EditGroupRequest
import com.kobietka.social_fitness_app.network.response.GetGroupResponse
import com.kobietka.social_fitness_app.util.Resource
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class EditRemoteGroupUseCase(private val groupRemoteRepository: GroupRemoteRepository){
    operator fun invoke(
        groupId: String,
        groupName: String,
        groupDescription: String
    ): Flow<Resource<GetGroupResponse>> = flow {
        emit(Resource.Loading<GetGroupResponse>())
        val result = groupRemoteRepository.editGroup(
            groupId = groupId,
            editGroupRequest = EditGroupRequest(
                id = groupId,
                name = groupName,
                description = groupDescription
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