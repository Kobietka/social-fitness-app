package com.kobietka.social_fitness_app.domain.usecase.event

import com.kobietka.social_fitness_app.domain.model.CreateEventValidationResult
import com.kobietka.social_fitness_app.domain.state.EventTypeState


class ValidateCreateEventUseCase {
    operator fun invoke(
        name: String,
        description: String,
        pointGoal: String,
        pointsPerMinute: String,
        pointsPerRepetition: String,
        startDate: String?,
        endDate: String?,
        eventType: EventTypeState
    ): List<CreateEventValidationResult> {
        val results = mutableListOf<CreateEventValidationResult>()

        if(name.isBlank())
            results.add(CreateEventValidationResult.NameBlank)
        if(description.isBlank())
            results.add(CreateEventValidationResult.DescriptionBlank)

        if(pointGoal.isBlank())
            results.add(CreateEventValidationResult.PointGoalBlank)
        else if(pointGoal.toInt() < 0)
            results.add(CreateEventValidationResult.PointGoalNegative)

        if(pointsPerMinute.isBlank())
            results.add(CreateEventValidationResult.PointsPerMinuteBlank)
        else if(pointsPerMinute.toInt() < 0)
            results.add(CreateEventValidationResult.PointsPerMinuteNegative)

        if(pointsPerRepetition.isBlank())
            results.add(CreateEventValidationResult.PointsPerRepetitionBlank)
        else if(pointsPerRepetition.toInt() < 0)
            results.add(CreateEventValidationResult.PointsPerRepetitionNegative)

        if(startDate == null)
            results.add(CreateEventValidationResult.StartDateNotSelected)
        if(endDate == null)
            results.add(CreateEventValidationResult.EndDateNotSelected)

        if(eventType.name == null)
            results.add(CreateEventValidationResult.EventTypeNotSelected)

        return results
    }
}
















