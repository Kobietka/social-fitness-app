package com.kobietka.social_fitness_app.domain.usecase.auth

import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository


class LogoutUserUseCase(
    private val userCredentialsRepository: UserCredentialsRepository,
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(){
        userCredentialsRepository.deleteAllCredentials()
        groupRepository.deleteAllGroups()
    }
}