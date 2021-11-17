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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kobietka.social_fitness_app.presentation.components.MultilineTextField
import com.kobietka.social_fitness_app.presentation.components.NumberTextField
import com.kobietka.social_fitness_app.presentation.components.StandardTextField
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
            .padding(20.dp)
            .verticalScroll(scroll)
    ) {
        state.group?.let { group ->
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
        NumberTextField(
            text = pointPerRepetition.text,
            error = pointPerRepetition.error,
            label = pointPerRepetition.label,
            onValueChange = createEventViewModel::onPointPerRepChange
        )
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clickable {
                    onStartDateClick { time ->
                        time?.let {
                            createEventViewModel.onStartDateChange(time)
                        }
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(startDate.formatted == null) {
                Text(text = "Select start date")
                if(startDate.error.isNotBlank()){
                    Text(
                        text = startDate.error,
                        color = MaterialTheme.colors.error
                    )
                }
            }
            else Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Start date")
                Text(
                    text = startDate.formatted,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clickable {
                    onEndDateClick { time ->
                        time?.let {
                            createEventViewModel.onEndDateChange(time)
                        }
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(endDate.formatted == null) {
                Text(text = "Select end date")
                if(endDate.error.isNotBlank()){
                    Text(
                        text = endDate.error,
                        color = MaterialTheme.colors.error
                    )
                }
            }
            else Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "End date")
                Text(
                    text = endDate.formatted,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clickable { createEventViewModel.onEventTypeClick() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(eventType.name == null) {
                Text(text = "Select event type")
                if(eventType.error.isNotBlank()){
                    Text(
                        text = eventType.error,
                        color = MaterialTheme.colors.error
                    )
                }
            }
            else Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Event type")
                Text(
                    text = eventType.name,
                    fontWeight = FontWeight.Medium
                )
            }
            DropdownMenu(
                expanded = dropdownMenuExpanded,
                onDismissRequest = createEventViewModel::onMenuDismiss,
                offset = DpOffset(x = 120.dp, y = 0.dp)
            ) {
                eventTypes.forEach { eventType ->
                    DropdownMenuItem(onClick = {
                        createEventViewModel.onEventTypeSelected(code = eventType.code!!)
                    }) {
                        Text(text = eventType.name!!)
                    }
                }
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)
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














