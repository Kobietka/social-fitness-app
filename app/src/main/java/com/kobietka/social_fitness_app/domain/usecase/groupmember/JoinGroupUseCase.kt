package com.kobietka.social_fitness_app.domain.usecase.groupmember

import com.kobietka.social_fitness_app.domain.repository.remote.GroupMemberRemoteRepository
import com.kobietka.social_fitness_app.network.request.JoinGroupRequest
import com.kobietka.social_fitness_app.network.response.MemberDto
import com.kobietka.social_fitness_app.util.Resource
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class JoinGroupUseCase(private val groupMemberRemoteRepository: GroupMemberRemoteRepository){
    operator fun invoke(
        code: String
    ): Flow<Resource<MemberDto>> = flow {
        emit(Resource.Loading<MemberDto>())
        val result = groupMemberRemoteRepository.joinGroup(
            joinGroupRequest = JoinGroupRequest(code = code)
        )
        when(result){
            is Result.Success -> {
                result.data?.let { response ->
                    emit(Resource.Success<MemberDto>(data = response))
                }
            }
            is Result.Failure -> {
                result.message?.let { message ->
                    emit(Resource.Error<MemberDto>(message = message))
                }
            }
            is Result.Unauthorized -> {
                emit(Resource.Unauthorized<MemberDto>())
            }
        }
    }
}