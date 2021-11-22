package com.kobietka.social_fitness_app.presentation.edit_event

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kobietka.social_fitness_app.presentation.components.InactiveDateSelector
import com.kobietka.social_fitness_app.presentation.components.MultilineTextField
import com.kobietka.social_fitness_app.presentation.components.NumberTextField
import com.kobietka.social_fitness_app.presentation.components.StandardTextField
import com.kobietka.social_fitness_app.presentation.edit_event.components.InactiveEventTypeSelector


@Composable
fun EditEventScreen(
    editEventViewModel: EditEventViewModel = hiltViewModel(),
    onSuccessfulEventEdit: () -> Unit
) {
    val state = editEventViewModel.state.value
    val eventName = editEventViewModel.eventName.value
    val eventDescription = editEventViewModel.eventDescription.value
    val startDate = editEventViewModel.startDate.value
    val endDate = editEventViewModel.endDate.value
    val eventType = editEventViewModel.eventType.value
    val pointGoal = editEventViewModel.pointGoal.value
    val pointsPerMinute = editEventViewModel.pointsPerMinute.value
    val pointsPerRepetition = editEventViewModel.pointsPerRepetition.value

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.Center
    ){
        state.event?.let { event ->
            Column(modifier = Modifier.padding(top = 20.dp)) {
                Text(
                    fontStyle = FontStyle.Italic,
                    text = "Editing event",
                    color = Color.Gray,
                    fontSize = 20.sp
                )
                Text(
                    text = event.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
        }
        state.error?.let { errorMessage ->
            Text(
                modifier = Modifier.padding(bottom = 30.dp),
                text = errorMessage,
                color = MaterialTheme.colors.error,
                fontWeight = FontWeight.Bold
            )
        }
        StandardTextField(
            text = eventName.text,
            error = eventName.error,
            label = eventName.label,
            onValueChange = editEventViewModel::onEventNameChange
        )
        MultilineTextField(
            text = eventDescription.text,
            error = eventDescription.error,
            label = eventDescription.label,
            maxLines = 5,
            onValueChange = editEventViewModel::onEventDescriptionChange
        )
        startDate?.let { date ->
            InactiveDateSelector(dateText = date, dateType = "Start")
        }
        endDate?.let { date ->
            InactiveDateSelector(dateText = date, dateType = "End")
        }
        eventType?.let { type ->
            InactiveEventTypeSelector(selectedType = type)
            when(type.code){
                "TIME" -> {
                    NumberTextField(
                        text = pointGoal.text,
                        error = pointGoal.error,
                        label = pointGoal.label,
                        onValueChange = editEventViewModel::onPointGoalChange
                    )
                    NumberTextField(
                        text = pointsPerMinute.text,
                        error = pointsPerMinute.error,
                        label = pointsPerMinute.label,
                        onValueChange = editEventViewModel::onPointsPerMinuteChange
                    )
                }
                "REP" -> {
                    NumberTextField(
                        text = pointGoal.text,
                        error = pointGoal.error,
                        label = pointGoal.label,
                        onValueChange = editEventViewModel::onPointGoalChange
                    )
                    NumberTextField(
                        text = pointsPerRepetition.text,
                        error = pointsPerRepetition.error,
                        label = pointsPerRepetition.label,
                        onValueChange = editEventViewModel::onPointsPerRepetitionChange
                    )
                }
                else -> { }
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp, bottom = 13.dp)
                .height(50.dp),
            onClick = {
                editEventViewModel.onEditEventClick {
                    onSuccessfulEventEdit()
                }
            },
            enabled = !state.isLoading
        ) {
            if(!state.isLoading) Text("Edit Event")
            else CircularProgressIndicator()
        }
    }

}

























