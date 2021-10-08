package com.kobietka.social_fitness_app.domain.usecase.edit_user

import com.kobietka.social_fitness_app.domain.model.UpdatePasswordValidationResult


class ValidateUpdatePasswordUseCase {
    operator fun invoke(
        newPassword: String,
        oldPassword: String
    ): UpdatePasswordValidationResult {
        if(newPassword.length < 8)
            return UpdatePasswordValidationResult.NewPasswordTooShort
        if(oldPassword.isBlank())
            return UpdatePasswordValidationResult.PasswordBlank

        return UpdatePasswordValidationResult.Success
    }
}