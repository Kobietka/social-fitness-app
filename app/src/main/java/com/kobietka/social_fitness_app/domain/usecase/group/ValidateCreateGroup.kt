package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.domain.model.CreateGroupValidationResult


class ValidateCreateGroup {
    operator fun invoke(
        groupName: String,
        groupDescription: String
    ): CreateGroupValidationResult {
        if(groupName.isBlank())
            return CreateGroupValidationResult.GroupNameBlank
        if(groupDescription.isBlank())
            return CreateGroupValidationResult.GroupDescriptionBlank

        return CreateGroupValidationResult.Success
    }
}