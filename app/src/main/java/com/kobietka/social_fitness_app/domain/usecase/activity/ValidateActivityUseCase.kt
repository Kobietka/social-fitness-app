package com.kobietka.social_fitness_app.domain.usecase.activity

import com.kobietka.social_fitness_app.domain.model.ActivityValidationResult


class ValidateActivityUseCase {
    operator fun invoke(
        activityName: String,
        repetitions: String,
        minutes: String,
        seconds: String
    ): List<ActivityValidationResult> {
        val results = mutableListOf<ActivityValidationResult>()

        if(activityName.isBlank())
            results.add(ActivityValidationResult.NameBlank)

        if(repetitions.isBlank())
            results.add(ActivityValidationResult.RepetitionsBlank)
        else {
            try {
                val repetitionsInt = repetitions.toInt()
                if(repetitionsInt < 0)
                    results.add(ActivityValidationResult.RepetitionsNegative)
            } catch (exception: NumberFormatException){
                results.add(ActivityValidationResult.RepetitionsNotANumber)
            }
        }

        if(minutes.isBlank())
            results.add(ActivityValidationResult.MinutesBlank)
        else {
            try {
                val minutesInt = minutes.toInt()
                if(minutesInt < 0)
                    results.add(ActivityValidationResult.MinutesNegative)
            } catch (exception: NumberFormatException){
                results.add(ActivityValidationResult.MinutesNotANumber)
            }
        }

        if(seconds.isBlank())
            results.add(ActivityValidationResult.SecondsBlank)
        else {
            try {
                val secondsInt = seconds.toInt()
                if(secondsInt < 0)
                    results.add(ActivityValidationResult.SecondsNegative)
            } catch (exception: NumberFormatException){
                results.add(ActivityValidationResult.SecondsNotANumber)
            }
        }

        return results
    }
}
















