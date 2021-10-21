package com.kobietka.social_fitness_app.domain.usecase.auth

import com.kobietka.social_fitness_app.data.entity.UserCredentialsEntity
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import javax.inject.Inject


class InsertUserCredentialsUseCase
@Inject constructor(private val userCredentialsRepository: UserCredentialsRepository){
    suspend operator fun invoke(
        token: String,
        id: String,
        nickname: String,
        email: String
    ){
        userCredentialsRepository.insert(
            UserCredentialsEntity(
                token = token,
                id = id,
                nickname = nickname,
                email = email
            )
        )
    }
}