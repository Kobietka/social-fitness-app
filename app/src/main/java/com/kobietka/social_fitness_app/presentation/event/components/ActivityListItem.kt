package com.kobietka.social_fitness_app.presentation.event.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kobietka.social_fitness_app.domain.model.Activity
import com.kobietka.social_fitness_app.domain.model.EventType


@Composable
fun ActivityListItem(
    activity: Activity,
    eventType: EventType
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp, bottom = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = activity.name,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = activity.createdAt,
                color = Color.Gray
            )
            Text(
                text = when(eventType){
                    EventType.REPETITION -> activity.value.toString() + " repetitions"
                    EventType.TIME -> {
                        val minutes = activity.value / 60
                        val seconds = activity.value - minutes * 60
                        "$minutes ${if(minutes == 1) "minute" else "minutes"} $seconds ${if(seconds == 1) "second" else "seconds"}"
                    }
                    EventType.LESS_TIME -> {
                        val minutes = activity.value / 60
                        val seconds = activity.value - minutes * 60
                        "$minutes ${if(minutes == 1) "minute" else "minutes"} $seconds ${if(seconds == 1) "second" else "seconds"}"
                    }
                    else -> ""
                }
            )
        }
    }
}












