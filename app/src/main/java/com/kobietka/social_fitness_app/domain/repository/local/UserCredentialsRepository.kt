package com.kobietka.social_fitness_app.domain.repository.local

import com.kobietka.social_fitness_app.data.entity.UserCredentialsEntity
import kotlinx.coroutines.flow.Flow


interface UserCredentialsRepository {
    suspend fun insert(userCredentialsEntity: UserCredentialsEntity)
    suspend fun deleteAllCredentials()
    fun getAllUsers(): Flow<List<UserCredentialsEntity>>
}