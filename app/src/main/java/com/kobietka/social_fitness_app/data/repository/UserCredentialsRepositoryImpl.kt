package com.kobietka.social_fitness_app.data.repository

import com.kobietka.social_fitness_app.data.dao.UserCredentialsDao
import com.kobietka.social_fitness_app.data.entity.UserCredentialsEntity
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import kotlinx.coroutines.flow.Flow


class UserCredentialsRepositoryImpl(
    private val userCredentialsDao: UserCredentialsDao
): UserCredentialsRepository {
    override suspend fun insert(userCredentialsEntity: UserCredentialsEntity) {
        userCredentialsDao.insert(userCredentialsEntity = userCredentialsEntity)
    }

    override suspend fun deleteAllCredentials() {
        userCredentialsDao.deleteAllCredentials()
    }

    override fun getAllUsers(): Flow<List<UserCredentialsEntity>> {
        return userCredentialsDao.getAllUsers()
    }

    override suspend fun getUserToken(): String {
        return userCredentialsDao.getUsers().first().token
    }

    override suspend fun getUserId(): String {
        return userCredentialsDao.getUsers().first().id
    }

    override suspend fun updateUserData(id: String, nickname: String, email: String) {
        userCredentialsDao.updateUserData(
            id = id,
            nickname = nickname,
            email = email
        )
    }
}