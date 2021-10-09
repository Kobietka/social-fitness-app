package com.kobietka.social_fitness_app.domain.usecase.edit_user

import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository


class InsertUpdatedUserDataUseCase(private val userCredentialsRepository: UserCredentialsRepository) {
    suspend operator fun invoke(
        id: String,
        nickname: String,
        email: String
    ) {
        userCredentialsRepository.updateUserData(
            id = id,
            nickname = nickname,
            email = email
        )
    }
}