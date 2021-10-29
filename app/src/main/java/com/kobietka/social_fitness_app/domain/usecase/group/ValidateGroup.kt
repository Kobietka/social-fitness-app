package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.domain.model.GroupValidationResult


class ValidateGroup {
    operator fun invoke(
        groupName: String,
        groupDescription: String
    ): GroupValidationResult {
        if(groupName.isBlank())
            return GroupValidationResult.GroupNameBlank
        if(groupDescription.isBlank())
            return GroupValidationResult.GroupDescriptionBlank

        return GroupValidationResult.Success
    }
}