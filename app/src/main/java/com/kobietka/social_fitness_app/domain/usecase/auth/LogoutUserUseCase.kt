package com.kobietka.social_fitness_app.domain.usecase.auth

import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository


class LogoutUserUseCase(private val userCredentialsRepository: UserCredentialsRepository) {
    suspend operator fun invoke(){
        userCredentialsRepository.deleteAllCredentials()
    }
}