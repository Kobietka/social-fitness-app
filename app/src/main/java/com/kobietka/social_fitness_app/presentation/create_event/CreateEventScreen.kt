package com.kobietka.social_fitness_app.presentation.create_event

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kobietka.social_fitness_app.presentation.components.DateSelector
import com.kobietka.social_fitness_app.presentation.components.MultilineTextField
import com.kobietka.social_fitness_app.presentation.components.NumberTextField
import com.kobietka.social_fitness_app.presentation.components.StandardTextField
import com.kobietka.social_fitness_app.presentation.create_event.components.EventTypeSelector
import kotlin.time.ExperimentalTime


@ExperimentalTime
@Composable
fun CreateEventScreen(
    createEventViewModel: CreateEventViewModel = hiltViewModel(),
    onStartDateClick: ((Long?) -> Unit) -> Unit,
    onEndDateClick: ((Long?) -> Unit) -> Unit,
    onSuccessEventCreation: () -> Unit
) {
    val state = createEventViewModel.state.value
    val eventName = createEventViewModel.eventName.value
    val eventDescription = createEventViewModel.eventDescription.value
    val pointGoal = createEventViewModel.pointGoal.value
    val pointPerRepetition = createEventViewModel.pointPerRep.value
    val pointPerMinute = createEventViewModel.pointPerMin.value
    val startDate = createEventViewModel.startDate.value
    val endDate = createEventViewModel.endDate.value
    val eventType = createEventViewModel.eventType.value
    val eventTypes = createEventViewModel.eventTypes.value
    val dropdownMenuExpanded = createEventViewModel.dropdownMenuExpanded.value

    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .verticalScroll(scroll),
        verticalArrangement = Arrangement.Center
    ) {
        state.group?.let { group ->
            Column(modifier = Modifier.padding(top = 20.dp)) {
                Text(
                    fontStyle = FontStyle.Italic,
                    text = "Creating event for",
                    color = Color.Gray,
                    fontSize = 20.sp
                )
                Text(
                    text = group.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
        }
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
            maxLines = 4,
            onValueChange = createEventViewModel::onEventDescriptionChange
        )
        DateSelector(
            dateText = startDate.formatted ?: "",
            dateType = "Start",
            error = startDate.error
        ) {
            onStartDateClick { time ->
                time?.let {
                    createEventViewModel.onStartDateChange(time)
                }
            }
        }
        DateSelector(
            dateText = endDate.formatted ?: "",
            dateType = "End",
            error = endDate.error
        ) {
            onEndDateClick { time ->
                time?.let {
                    createEventViewModel.onEndDateChange(time)
                }
            }
        }
        EventTypeSelector(
            types = eventTypes,
            selectedType = eventType,
            onIconClick = createEventViewModel::onEventTypeClick,
            onTypeClick = createEventViewModel::onEventTypeSelected,
            onDismiss = createEventViewModel::onMenuDismiss,
            isExpanded = dropdownMenuExpanded,
            error = eventType.error
        )
        eventType.code?.let { eventCode ->
            when(eventCode){
                "TIME" -> {
                    NumberTextField(
                        text = pointGoal.text,
                        error = pointGoal.error,
                        label = pointGoal.label,
                        onValueChange = createEventViewModel::onPointGoalChange
                    )
                    NumberTextField(
                        text = pointPerMinute.text,
                        error = pointPerMinute.error,
                        label = pointPerMinute.label,
                        onValueChange = createEventViewModel::onPointPerMinChange
                    )
                }
                "REP" -> {
                    NumberTextField(
                        text = pointGoal.text,
                        error = pointGoal.error,
                        label = pointGoal.label,
                        onValueChange = createEventViewModel::onPointGoalChange
                    )
                    NumberTextField(
                        text = pointPerRepetition.text,
                        error = pointPerRepetition.error,
                        label = pointPerRepetition.label,
                        onValueChange = createEventViewModel::onPointPerRepChange
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
                createEventViewModel.onCreateEventClick {
                    onSuccessEventCreation()
                }
            },
            enabled = !state.isLoading
        ) {
            if(!state.isLoading) Text("Create Event")
            else CircularProgressIndicator()
        }
    }

}














