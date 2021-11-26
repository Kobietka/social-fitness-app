package com.kobietka.social_fitness_app.presentation.create_activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kobietka.social_fitness_app.domain.model.EventType
import com.kobietka.social_fitness_app.presentation.components.NumberTextField
import com.kobietka.social_fitness_app.presentation.components.StandardTextField


@Composable
fun CreateActivityScreen(
    createActivityViewModel: CreateActivityViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {
    val state = createActivityViewModel.state.value
    val activityName = createActivityViewModel.activityName.value
    val repetitions = createActivityViewModel.repetitions.value
    val minutes = createActivityViewModel.minutes.value
    val seconds = createActivityViewModel.seconds.value

    Scaffold(
        topBar = {
            Surface(elevation = 8.dp) {
                state.event?.let { event ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            fontStyle = FontStyle.Italic,
                            text = "Creating activity for",
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
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                state.error?.let { message ->
                    Text(
                        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                        text = message,
                        color = MaterialTheme.colors.error,
                        fontWeight = FontWeight.Bold
                    )
                }
                StandardTextField(
                    text = activityName.text,
                    error = activityName.error,
                    label = activityName.label,
                    onValueChange = createActivityViewModel::onActivityNameChange
                )
                state.event?.let { event ->
                    when(event.eventType){
                        EventType.REPETITION -> {
                            NumberTextField(
                                text = repetitions.text,
                                error = repetitions.error,
                                label = repetitions.label,
                                onValueChange = createActivityViewModel::onRepetitionsChange
                            )
                        }
                        EventType.TIME -> {
                            NumberTextField(
                                text = minutes.text,
                                error = minutes.error,
                                label = minutes.label,
                                onValueChange = createActivityViewModel::onMinutesChange
                            )
                            NumberTextField(
                                text = seconds.text,
                                error = seconds.error,
                                label = seconds.label,
                                onValueChange = createActivityViewModel::onSecondsChange
                            )
                        }
                        EventType.LESS_TIME -> {
                            NumberTextField(
                                text = minutes.text,
                                error = minutes.error,
                                label = minutes.label,
                                onValueChange = createActivityViewModel::onMinutesChange
                            )
                            NumberTextField(
                                text = seconds.text,
                                error = seconds.error,
                                label = seconds.label,
                                onValueChange = createActivityViewModel::onSecondsChange
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
                        createActivityViewModel.onCreateActivityClick { onSuccess() }
                    },
                    enabled = !state.isLoading
                ) {
                    if(!state.isLoading) Text("Create activity")
                    else CircularProgressIndicator()
                }
            }
        }
    }

}


















