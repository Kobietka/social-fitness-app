package com.kobietka.social_fitness_app.presentation.create_event

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kobietka.social_fitness_app.presentation.components.MultilineTextField
import com.kobietka.social_fitness_app.presentation.components.StandardTextField


@Composable
fun CreateEventScreen(
    createEventViewModel: CreateEventViewModel = hiltViewModel(),
    onStartDateClick: ((Long?) -> Unit) -> Unit
) {
    val eventName = createEventViewModel.eventName.value
    val eventDescription = createEventViewModel.eventDescription.value
    val pointGoal = createEventViewModel.pointGoal.value
    val pointPerRepetition = createEventViewModel.pointPerRep.value
    val pointPerMinute = createEventViewModel.pointPerMin.value
    val startDate = createEventViewModel.startDate.value
    val endDate = createEventViewModel.endDate.value
    val eventType = createEventViewModel.eventType.value


    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        StandardTextField(
            text = eventName.text,
            error = eventName.error,
            label = eventName.label,
            onValueChange = createEventViewModel::onEventNameChange
        )
        MultilineTextField(
            text = eventDescription.text,
            error = eventDescription.error,
            label = eventDescription.label,
            maxLines = 5,
            onValueChange = createEventViewModel::onEventDescriptionChange
        )
        StandardTextField(
            text = pointGoal.text,
            error = pointGoal.error,
            label = pointGoal.label,
            onValueChange = createEventViewModel::onPointGoalChange
        )
        StandardTextField(
            text = pointPerMinute.text,
            error = pointPerMinute.error,
            label = pointPerMinute.label,
            onValueChange = createEventViewModel::onPointPerMinChange
        )
        StandardTextField(
            text = pointPerRepetition.text,
            error = pointPerRepetition.error,
            label = pointPerRepetition.label,
            onValueChange = createEventViewModel::onPointPerRepChange
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable { onStartDateClick { time ->
                    time?.let {
                        createEventViewModel.onStartDateChange(time)
                    }
                } },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(startDate == null) Text(text = "Select start date")
            else Text(text = startDate)
        }
    }

}














