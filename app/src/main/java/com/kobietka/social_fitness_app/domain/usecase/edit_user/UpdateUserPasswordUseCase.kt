package com.kobietka.social_fitness_app.domain.usecase.edit_user

import com.kobietka.social_fitness_app.domain.repository.remote.UpdateUserRemoteRepository
import com.kobietka.social_fitness_app.network.request.UpdateUserPasswordRequest
import com.kobietka.social_fitness_app.util.Resource
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UpdateUserPasswordUseCase(private val updateUserRemoteRepository: UpdateUserRemoteRepository) {
    operator fun invoke(
        userId: String,
        currentPassword: String,
        newPassword: String
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>())
        val result = updateUserRemoteRepository.updateUserPassword(
            userId = userId,
            updateUserPasswordRequest = UpdateUserPasswordRequest(
                id = userId,
                password = newPassword,
                oldPassword = currentPassword
            )
        )
        when(result){
            is Result.Success<Boolean> -> {
                result.data?.let {
                    emit(Resource.Success<Boolean>(data = it))
                }
            }
            is Result.Failure<Boolean> -> {
                result.message?.let { message ->
                    emit(Resource.Error<Boolean>(message = message))
                }
            }
        }
    }
}















