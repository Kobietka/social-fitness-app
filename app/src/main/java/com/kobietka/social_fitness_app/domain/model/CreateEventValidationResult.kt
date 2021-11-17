package com.kobietka.social_fitness_app.domain.model



sealed class CreateEventValidationResult {
    object NameBlank : CreateEventValidationResult()
    object DescriptionBlank : CreateEventValidationResult()
    object PointGoalBlank : CreateEventValidationResult()
    object PointsPerMinuteBlank : CreateEventValidationResult()
    object PointsPerRepetitionBlank : CreateEventValidationResult()
    object PointGoalNegative : CreateEventValidationResult()
    object PointsPerMinuteNegative : CreateEventValidationResult()
    object PointsPerRepetitionNegative : CreateEventValidationResult()
    object StartDateNotSelected : CreateEventValidationResult()
    object EndDateNotSelected : CreateEventValidationResult()
    object EventTypeNotSelected : CreateEventValidationResult()
}
