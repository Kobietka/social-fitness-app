package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.network.response.GetGroupResponse
import com.kobietka.social_fitness_app.util.Resource
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetRemoteGroupsUseCase(private val groupRemoteRepository: GroupRemoteRepository) {
    operator fun invoke(): Flow<Resource<List<GetGroupResponse>>> = flow {
        emit(Resource.Loading<List<GetGroupResponse>>())
        when(val result = groupRemoteRepository.getGroups()){
            is Result.Success -> {
                result.data?.let { response ->
                    emit(Resource.Success<List<GetGroupResponse>>(data = response))
                }
            }
            is Result.Failure -> {
                result.message?.let { message ->
                    emit(Resource.Error<List<GetGroupResponse>>(message = message))
                }
            }
            is Result.Unauthorized -> {
                emit(Resource.Unauthorized<List<GetGroupResponse>>())
            }
        }
    }
}