package com.kobietka.social_fitness_app.domain.usecase.auth

import com.google.common.truth.Truth
import com.kobietka.social_fitness_app.domain.model.RegisterValidationResult
import org.junit.Test


class ValidateRegisterUseCaseTest {

    @Test
    fun validationSuccess(){
        val validateRegisterUserData = ValidateRegisterUseCase()
        val validationResult = validateRegisterUserData(
            nickname = "nickname",
            email = "example@example.com",
            password = "12345678",
            repeatPassword = "12345678"
        )

        Truth.assertThat(validationResult)
            .isInstanceOf(RegisterValidationResult.Success::class.java)
    }

    @Test
    fun validationEmailNotValid(){
        val validateRegisterUserData = ValidateRegisterUseCase()
        val validationResult = validateRegisterUserData(
            nickname = "nickname",
            email = "example",
            password = "12345678",
            repeatPassword = "12345678"
        )

        Truth.assertThat(validationResult)
            .isInstanceOf(RegisterValidationResult.EmailNotValid::class.java)
    }

    @Test
    fun validationPasswordTooShort(){
        val validateRegisterUserData = ValidateRegisterUseCase()
        val validationResult = validateRegisterUserData(
            nickname = "nickname",
            email = "example@example.com",
            password = "1234",
            repeatPassword = "12345678"
        )

        Truth.assertThat(validationResult)
            .isInstanceOf(RegisterValidationResult.PasswordTooShort::class.java)
    }

    @Test
    fun validationNicknameTooShort(){
        val validateRegisterUserData = ValidateRegisterUseCase()
        val validationResult = validateRegisterUserData(
            nickname = "ni",
            email = "example@example.com",
            password = "12345678",
            repeatPassword = "12345678"
        )

        Truth.assertThat(validationResult)
            .isInstanceOf(RegisterValidationResult.NicknameTooShort::class.java)
    }

    @Test
    fun validationPasswordsDoNotMatch(){
        val validateRegisterUserData = ValidateRegisterUseCase()
        val validationResult = validateRegisterUserData(
            nickname = "nickname",
            email = "example@example.com",
            password = "12345678",
            repeatPassword = "87654321"
        )

        Truth.assertThat(validationResult)
            .isInstanceOf(RegisterValidationResult.PasswordsAreNotTheSame::class.java)
    }

}