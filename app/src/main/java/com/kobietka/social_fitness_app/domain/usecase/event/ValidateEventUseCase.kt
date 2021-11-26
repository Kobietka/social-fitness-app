package com.kobietka.social_fitness_app.domain.usecase.event

import com.kobietka.social_fitness_app.domain.model.EventValidationResult
import com.kobietka.social_fitness_app.domain.state.EventTypeState


class ValidateEventUseCase {
    operator fun invoke(
        name: String,
        description: String,
        pointGoal: String,
        pointsPerMinute: String,
        pointsPerRepetition: String,
        startDate: String?,
        endDate: String?,
        eventType: EventTypeState
    ): List<EventValidationResult> {
        val results = mutableListOf<EventValidationResult>()

        if(name.isBlank())
            results.add(EventValidationResult.NameBlank)
        if(description.isBlank())
            results.add(EventValidationResult.DescriptionBlank)

        if(pointGoal.isBlank())
            results.add(EventValidationResult.PointGoalBlank)
        else {
            try {
                val pointGoalInt = pointGoal.toInt()
                if(pointGoalInt < 0)
                    results.add(EventValidationResult.PointGoalNegative)
            } catch (exception: NumberFormatException){
                results.add(EventValidationResult.PointGoalNotANumber)
            }
        }

        if(pointsPerMinute.isBlank())
            results.add(EventValidationResult.PointsPerMinuteBlank)
        else {
            try {
                if(pointsPerMinute.toInt() < 0)
                    results.add(EventValidationResult.PointsPerMinuteNegative)
            } catch (exception: NumberFormatException){
                results.add(EventValidationResult.PointsPerMinuteNotANumber)
            }
        }

        if(pointsPerRepetition.isBlank())
            results.add(EventValidationResult.PointsPerRepetitionBlank)
        else {
            try {
                if(pointsPerRepetition.toInt() < 0)
                    results.add(EventValidationResult.PointsPerRepetitionNegative)
            } catch (exception: NumberFormatException){
                results.add(EventValidationResult.PointsPerRepetitionNotANumber)
            }
        }

        if(startDate == null)
            results.add(EventValidationResult.StartDateNotSelected)
        if(endDate == null)
            results.add(EventValidationResult.EndDateNotSelected)

        if(eventType.name == null)
            results.add(EventValidationResult.EventTypeNotSelected)

        return results
    }
}
















