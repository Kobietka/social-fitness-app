package com.kobietka.social_fitness_app.domain.model



sealed class EventValidationResult {
    object NameBlank : EventValidationResult()
    object DescriptionBlank : EventValidationResult()
    object PointGoalBlank : EventValidationResult()
    object PointsPerMinuteBlank : EventValidationResult()
    object PointsPerRepetitionBlank : EventValidationResult()
    object PointGoalNegative : EventValidationResult()
    object PointsPerMinuteNegative : EventValidationResult()
    object PointsPerRepetitionNegative : EventValidationResult()
    object StartDateNotSelected : EventValidationResult()
    object EndDateNotSelected : EventValidationResult()
    object EventTypeNotSelected : EventValidationResult()
    object PointGoalNotANumber : EventValidationResult()
    object PointsPerMinuteNotANumber : EventValidationResult()
    object PointsPerRepetitionNotANumber : EventValidationResult()
}
