package com.kobietka.social_fitness_app.presentation.event

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kobietka.social_fitness_app.domain.model.EventType
import com.kobietka.social_fitness_app.presentation.event.components.DividedProperty
import com.kobietka.social_fitness_app.presentation.event.components.EventMemberListItem


@ExperimentalAnimationApi
@Composable
fun EventScreen(
    eventViewModel: EventViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = eventViewModel.state.value

    Scaffold(
        floatingActionButton = {
            state.event?.let { event ->
                if(event.isActive) FloatingActionButton(
                    onClick = { navController.navigate("/event/${event.id}/create_activity") },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "create activity"
                    )
                }
            }
        },
        topBar = {
            Surface(elevation = 8.dp) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    state.event?.let { event ->
                        Text(
                            modifier = Modifier
                                .padding(20.dp),
                            text = event.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if(state.isUserAGroupOwner && event.isActive) IconButton(
                                onClick = {
                                    state.group?.let { group ->
                                        navController.navigate(
                                            route = "/group/${group.id}/edit_event/${event.id}"
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(30.dp),
                                    tint = Color.Black,
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = "edit event"
                                )
                            }
                        }
                    }
                }
            }
        }
    ) {
        state.event?.let { event ->
            LazyColumn(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            ) {
                item {
                    DividedProperty(name = "Event description", value = event.description)
                }
                item {
                    when(event.eventType){
                        EventType.LESS_TIME -> {
                            DividedProperty(
                                name = "Goal",
                                value = "The faster you go, the better."
                            )
                        }
                        else -> {
                            DividedProperty(
                                name = "Goal",
                                value = "You have to earn ${event.pointGoal} points to complete this event."
                            )
                        }
                    }
                }
                when(event.eventType){
                    EventType.REPETITION -> item {
                        DividedProperty(
                            name = "Points per repetition",
                            value = "You will get ${event.pointPerRep} ${if(event.pointPerRep == 1) "point" else "points"} per every repetition."
                        )
                    }
                    EventType.TIME -> item {
                        DividedProperty(
                            name = "Points per minute",
                            value = "You will get ${event.pointPerMinute} ${if(event.pointPerMinute == 1) "point" else "points"} per every minute you train."
                        )
                    }
                    else -> {}
                }
                item {
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        fontSize = 20.sp,
                        text = "Event Ranking",
                        fontWeight = FontWeight.Medium
                    )
                }
                if(state.eventMembers.isEmpty()) item {
                    Text(text = "No participants.\nAdd activity to get started.")
                } else {
                    items(state.eventMembers.filter { it.totalScore >= event.pointGoal }){ eventMember ->
                        EventMemberListItem(
                            eventMember = eventMember,
                            eventType = event.eventType
                        )
                    }
                    item {
                        if(event.eventType != EventType.LESS_TIME) Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Divider(
                                thickness = 2.dp,
                                modifier = Modifier.fillMaxWidth(0.40f),
                                color = Color.Green
                            )
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "winners",
                                tint = Color.Green
                            )
                            Divider(
                                thickness = 2.dp,
                                modifier = Modifier.fillMaxWidth(0.75f),
                                color = Color.Green
                            )
                        }
                    }
                    if(event.eventType != EventType.LESS_TIME) {
                        items(state.eventMembers.filter { it.totalScore < event.pointGoal }){ eventMember ->
                            EventMemberListItem(
                                eventMember = eventMember,
                                eventType = event.eventType
                            )
                        }
                    }
                }
            }
        }
    }
}














