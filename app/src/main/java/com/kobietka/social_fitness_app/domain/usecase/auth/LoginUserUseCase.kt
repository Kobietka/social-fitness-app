package com.kobietka.social_fitness_app.domain.usecase.auth

import com.kobietka.social_fitness_app.data.entity.UserCredentialsEntity
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import com.kobietka.social_fitness_app.domain.repository.remote.AuthRepository
import com.kobietka.social_fitness_app.network.request.LoginUserRequest
import com.kobietka.social_fitness_app.network.response.LoginUserResponse
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class LoginUserUseCase(
    private val authRepository: AuthRepository,
    private val userCredentialsRepository: UserCredentialsRepository
) {
    operator fun invoke(
        email: String,
        password: String
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        val result = authRepository.loginUser(
            loginUserRequest = LoginUserRequest(
                email = email,
                password = password
            )
        )
        when(result){
            is NetworkResult.Success -> {
                result.data.let { loginResponse ->
                    userCredentialsRepository.insert(
                        UserCredentialsEntity(
                            id = loginResponse.id,
                            token = loginResponse.token,
                            email = loginResponse.email,
                            nickname = loginResponse.nickname
                        )
                    )
                    emit(Progress.Finished)
                }
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}