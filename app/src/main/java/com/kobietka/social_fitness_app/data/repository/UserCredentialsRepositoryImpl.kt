package com.kobietka.social_fitness_app.data.repository

import com.kobietka.social_fitness_app.data.dao.UserCredentialsDao
import com.kobietka.social_fitness_app.data.entity.UserCredentialsEntity
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import javax.inject.Inject


class UserCredentialsRepositoryImpl
@Inject constructor(private val userCredentialsDao: UserCredentialsDao): UserCredentialsRepository {
    override suspend fun insert(userCredentialsEntity: UserCredentialsEntity) {
        userCredentialsDao.insert(userCredentialsEntity = userCredentialsEntity)
    }

    override suspend fun deleteAllCredentials() {
        userCredentialsDao.deleteAllCredentials()
    }
}