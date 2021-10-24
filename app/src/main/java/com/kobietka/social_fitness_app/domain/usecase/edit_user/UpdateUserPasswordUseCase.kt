package com.kobietka.social_fitness_app.domain.usecase.edit_user

import com.kobietka.social_fitness_app.domain.repository.remote.UpdateUserRemoteRepository
import com.kobietka.social_fitness_app.network.request.UpdateUserPasswordRequest
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UpdateUserPasswordUseCase(private val updateUserRemoteRepository: UpdateUserRemoteRepository) {
    operator fun invoke(
        userId: String,
        currentPassword: String,
        newPassword: String
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        val result = updateUserRemoteRepository.updateUserPassword(
            userId = userId,
            updateUserPasswordRequest = UpdateUserPasswordRequest(
                id = userId,
                password = newPassword,
                oldPassword = currentPassword
            )
        )
        when(result){
            is NetworkResult.Success -> emit(Progress.Finished)
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}















