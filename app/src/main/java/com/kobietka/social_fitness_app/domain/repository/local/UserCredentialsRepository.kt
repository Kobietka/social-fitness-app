package com.kobietka.social_fitness_app.domain.repository.local

import com.kobietka.social_fitness_app.data.entity.UserCredentialsEntity


interface UserCredentialsRepository {
    suspend fun insert(userCredentialsEntity: UserCredentialsEntity)
    suspend fun deleteAllCredentials()
}