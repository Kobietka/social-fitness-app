package com.kobietka.social_fitness_app.domain.usecase.edit_user

import com.kobietka.social_fitness_app.domain.model.UpdateUserDataValidationResult
import com.kobietka.social_fitness_app.presentation.register.isValidEmail


class ValidateUpdateUserDataUseCase {
    operator fun invoke(
        nickname: String,
        email: String,
        password: String
    ): UpdateUserDataValidationResult {
        if(nickname.length < 4)
            return UpdateUserDataValidationResult.NicknameTooShort
        if(!isValidEmail(email = email))
            return UpdateUserDataValidationResult.NotAValidEmail
        if(password.isBlank())
            return UpdateUserDataValidationResult.PasswordBlank

        return UpdateUserDataValidationResult.Success
    }
}