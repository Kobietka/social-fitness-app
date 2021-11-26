package com.kobietka.social_fitness_app.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


class DateUtil {

    fun isLocalDateBeforeNow(date: LocalDate): Boolean {
        val currentDate = Clock.System.now()
        val currentLocalDateTime = currentDate.toLocalDateTime(TimeZone.currentSystemDefault())

        val currentLocalDate = LocalDate(
            year = currentLocalDateTime.year,
            monthNumber = currentLocalDateTime.monthNumber,
            dayOfMonth = currentLocalDateTime.dayOfMonth
        )
        return date <= currentLocalDate
    }

    fun isLocalDateAfterNow(date: LocalDate): Boolean {
        val currentDate = Clock.System.now()
        val currentLocalDateTime = currentDate.toLocalDateTime(TimeZone.currentSystemDefault())

        val currentLocalDate = LocalDate(
            year = currentLocalDateTime.year,
            monthNumber = currentLocalDateTime.monthNumber,
            dayOfMonth = currentLocalDateTime.dayOfMonth
        )
        return date >= currentLocalDate
    }

    fun isNowBetweenDates(start: LocalDate, end: LocalDate): Boolean {
        return isLocalDateBeforeNow(start) && isLocalDateAfterNow(end)
    }

    /**
     * @param value in format "yyyy-mm-dd"
     */
    fun localDateFrom(value: String): LocalDate {
        val yearText = value.substringBefore("-")
        val dayText = value.substringAfterLast("-")
        val monthText = value.substringAfter("$yearText-").substringBefore("-")

        return LocalDate(
            year = yearText.toInt(),
            monthNumber = monthText.toInt(),
            dayOfMonth = dayText.toInt()
        )
    }

}