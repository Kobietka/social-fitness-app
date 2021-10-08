package com.kobietka.social_fitness_app.domain.usecase.main

import com.kobietka.social_fitness_app.data.entity.toUser
import com.kobietka.social_fitness_app.domain.model.User
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetUsersUseCase(private val userCredentialsRepository: UserCredentialsRepository) {
    operator fun invoke(): Flow<List<User>> {
        return userCredentialsRepository.getAllUsers().map { users ->
            users.map { user -> user.toUser() }
        }
    }
}