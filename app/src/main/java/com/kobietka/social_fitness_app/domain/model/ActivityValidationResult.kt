package com.kobietka.social_fitness_app.domain.model


sealed class ActivityValidationResult {
    object NameBlank : ActivityValidationResult()
    object RepetitionsBlank : ActivityValidationResult()
    object MinutesBlank : ActivityValidationResult()
    object SecondsBlank : ActivityValidationResult()
    object RepetitionsNegative : ActivityValidationResult()
    object MinutesNegative : ActivityValidationResult()
    object SecondsNegative : ActivityValidationResult()
    object RepetitionsNotANumber : ActivityValidationResult()
    object MinutesNotANumber : ActivityValidationResult()
    object SecondsNotANumber : ActivityValidationResult()
}