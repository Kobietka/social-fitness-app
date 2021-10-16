package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.util.Resource
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class DeleteRemoteGroupUseCase(private val groupRemoteRepository: GroupRemoteRepository) {
    operator fun invoke(groupId: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>())

        when(val result = groupRemoteRepository.deleteGroup(groupId = groupId)){
            is Result.Success -> {
                emit(Resource.Success<Boolean>(data = true))
            }
            is Result.Failure -> {
                result.message?.let { message ->
                    emit(Resource.Error<Boolean>(message = message))
                }
            }
            is Result.Unauthorized -> {
                emit(Resource.Unauthorized<Boolean>())
            }
        }
    }
}