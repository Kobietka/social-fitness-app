package com.kobietka.social_fitness_app.domain.usecase.edit_user

import com.kobietka.social_fitness_app.domain.repository.remote.UpdateUserRemoteRepository
import com.kobietka.social_fitness_app.network.request.UpdateUserDataRequest
import com.kobietka.social_fitness_app.network.response.UpdateUserDataResponse
import com.kobietka.social_fitness_app.util.Resource
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UpdateUserDataUseCase(private val updateUserRemoteRepository: UpdateUserRemoteRepository) {
    operator fun invoke(
        userId: String,
        nickname: String,
        email: String,
        password: String
    ): Flow<Resource<UpdateUserDataResponse>> = flow {
        emit(Resource.Loading<UpdateUserDataResponse>())
        val result = updateUserRemoteRepository.updateUserData(
            userId = userId,
            updateUserDataRequest = UpdateUserDataRequest(
                id = userId,
                nickname = nickname,
                email = email,
                password = password
            )
        )
        when(result){
            is Result.Success -> {
                result.data?.let { response ->
                    emit(Resource.Success<UpdateUserDataResponse>(data = response))
                }
            }
            is Result.Failure -> {
                result.message?.let { message ->
                    emit(Resource.Error<UpdateUserDataResponse>(message = message))
                }
            }
            is Result.Unauthorized -> {
                emit(Resource.Unauthorized<UpdateUserDataResponse>())
            }
        }
    }
}




















