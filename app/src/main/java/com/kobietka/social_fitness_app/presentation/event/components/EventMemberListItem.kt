package com.kobietka.social_fitness_app.presentation.event.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kobietka.social_fitness_app.domain.model.EventMember
import com.kobietka.social_fitness_app.domain.model.EventType


@ExperimentalAnimationApi
@Composable
fun EventMemberListItem(
    eventMember: EventMember,
    eventType: EventType
) {
    val expanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded.value = !expanded.value }
            .padding(top = 5.dp, bottom = 5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = eventMember.nickname,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            when(eventType){
                EventType.LESS_TIME -> {
                    val fastestActivity = eventMember.activities.minByOrNull { it.value }
                    fastestActivity?.let {
                        val minutes = fastestActivity.value / 60
                        val seconds = fastestActivity.value - minutes * 60
                        Text(text = "$minutes ${if(minutes == 1) "minute" else "minutes"} $seconds ${if(seconds == 1) "second" else "seconds"}")
                    }
                }
                else -> Text(text = eventMember.totalScore.toString() + " points")
            }
        }
        AnimatedVisibility(visible = expanded.value) {
            Column {
                eventMember.activities.forEach { activity ->
                    ActivityListItem(activity = activity, eventType = eventType)
                }
            }
        }
        Divider(modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
    }
}





















