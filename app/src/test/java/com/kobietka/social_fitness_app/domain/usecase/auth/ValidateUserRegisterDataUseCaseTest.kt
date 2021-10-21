package com.kobietka.social_fitness_app.domain.usecase.auth

import com.google.common.truth.Truth
import com.kobietka.social_fitness_app.domain.model.RegisterDataValidationResult
import org.junit.Test


class ValidateUserRegisterDataUseCaseTest {

    @Test
    fun validationSuccess(){
        val validateRegisterUserData = ValidateUserRegisterDataUseCase()
        val validationResult = validateRegisterUserData(
            nickname = "nickname",
            email = "example@example.com",
            password = "12345678",
            repeatPassword = "12345678"
        )

        Truth.assertThat(validationResult)
            .isInstanceOf(RegisterDataValidationResult.Success::class.java)
    }

    @Test
    fun validationEmailNotValid(){
        val validateRegisterUserData = ValidateUserRegisterDataUseCase()
        val validationResult = validateRegisterUserData(
            nickname = "nickname",
            email = "example",
            password = "12345678",
            repeatPassword = "12345678"
        )

        Truth.assertThat(validationResult)
            .isInstanceOf(RegisterDataValidationResult.EmailNotValid::class.java)
    }

    @Test
    fun validationPasswordTooShort(){
        val validateRegisterUserData = ValidateUserRegisterDataUseCase()
        val validationResult = validateRegisterUserData(
            nickname = "nickname",
            email = "example@example.com",
            password = "1234",
            repeatPassword = "12345678"
        )

        Truth.assertThat(validationResult)
            .isInstanceOf(RegisterDataValidationResult.PasswordTooShort::class.java)
    }

    @Test
    fun validationNicknameTooShort(){
        val validateRegisterUserData = ValidateUserRegisterDataUseCase()
        val validationResult = validateRegisterUserData(
            nickname = "ni",
            email = "example@example.com",
            password = "12345678",
            repeatPassword = "12345678"
        )

        Truth.assertThat(validationResult)
            .isInstanceOf(RegisterDataValidationResult.NicknameTooShort::class.java)
    }

    @Test
    fun validationPasswordsDoNotMatch(){
        val validateRegisterUserData = ValidateUserRegisterDataUseCase()
        val validationResult = validateRegisterUserData(
            nickname = "nickname",
            email = "example@example.com",
            password = "12345678",
            repeatPassword = "87654321"
        )

        Truth.assertThat(validationResult)
            .isInstanceOf(RegisterDataValidationResult.PasswordsAreNotTheSame::class.java)
    }

}