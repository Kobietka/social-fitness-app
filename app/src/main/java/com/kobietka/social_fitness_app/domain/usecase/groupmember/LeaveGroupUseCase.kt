package com.kobietka.social_fitness_app.domain.usecase.groupmember


import com.kobietka.social_fitness_app.domain.repository.remote.GroupMemberRemoteRepository
import com.kobietka.social_fitness_app.util.Resource
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LeaveGroupUseCase(private val groupMemberRemoteRepository: GroupMemberRemoteRepository){
    operator fun invoke(
        id: String
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>())
        when(val result = groupMemberRemoteRepository.leaveGroup(id = id)){
            is Result.Success -> {
                result.data?.let { response ->
                    emit(Resource.Success<Boolean>(data = response))
                }
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