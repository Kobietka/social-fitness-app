package com.kobietka.social_fitness_app.domain.usecase.edit_user

import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import com.kobietka.social_fitness_app.domain.repository.remote.UpdateUserRemoteRepository
import com.kobietka.social_fitness_app.network.request.UpdateUserDataRequest
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UpdateUserDataUseCase(
    private val updateUserRemoteRepository: UpdateUserRemoteRepository,
    private val userCredentialsRepository: UserCredentialsRepository
) {
    operator fun invoke(
        userId: String,
        nickname: String,
        email: String,
        password: String
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
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
            is NetworkResult.Success -> {
                result.data.let { response ->
                    userCredentialsRepository.updateUserData(
                        id = response.id,
                        email = response.email,
                        nickname = response.nickname
                    )
                    emit(Progress.Finished)
                }
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}




















