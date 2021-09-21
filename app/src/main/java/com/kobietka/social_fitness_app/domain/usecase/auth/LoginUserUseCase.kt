package com.kobietka.social_fitness_app.domain.usecase.auth

import com.kobietka.social_fitness_app.domain.repository.remote.AuthRepository
import com.kobietka.social_fitness_app.network.request.LoginUserRequest
import com.kobietka.social_fitness_app.network.response.LoginUserResponse
import com.kobietka.social_fitness_app.util.Resource
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LoginUserUseCase
@Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke(
        email: String,
        password: String
    ): Flow<Resource<LoginUserResponse>> = flow {
        emit(Resource.Loading<LoginUserResponse>())
        val result = authRepository.loginUser(
            loginUserRequest = LoginUserRequest(
                email = email,
                password = password
            )
        )
        when(result){
            is Result.Success<LoginUserResponse> -> emit(Resource.Success<LoginUserResponse>(result.data!!))
            is Result.Failure<LoginUserResponse> -> emit(Resource.Error<LoginUserResponse>(result.message!!))
        }
    }
}