package com.kobietka.social_fitness_app.domain.usecase.auth

import com.kobietka.social_fitness_app.domain.repository.remote.AuthRepository
import com.kobietka.social_fitness_app.network.request.RegisterUserRequest
import com.kobietka.social_fitness_app.util.Resource
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RegisterUserUseCase
@Inject constructor(private val authRepository: AuthRepository){
    operator fun invoke(
        nickname: String,
        email: String,
        password: String
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>())
        val result = authRepository.registerUser(
            registerUserRequest = RegisterUserRequest(
                nickname = nickname,
                email = email,
                password = password
            )
        )
        when(result){
            is Result.Success<Boolean> -> emit(Resource.Success<Boolean>(result.data!!))
            is Result.Failure<Boolean> -> emit(Resource.Error<Boolean>(result.message!!))
        }
    }
}