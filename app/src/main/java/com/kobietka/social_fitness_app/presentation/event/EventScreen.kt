package com.kobietka.social_fitness_app.presentation.event

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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


@Composable
fun EventScreen(
    eventViewModel: EventViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = eventViewModel.state.value

    Scaffold(
        floatingActionButton = {
            state.event?.let { event ->
                FloatingActionButton(
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
                            if(state.isUserAGroupOwner) IconButton(
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

    }
}