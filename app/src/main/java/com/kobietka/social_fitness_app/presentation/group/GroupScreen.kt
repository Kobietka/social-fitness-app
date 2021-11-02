package com.kobietka.social_fitness_app.presentation.group

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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


@ExperimentalAnimationApi
@Composable
fun GroupScreen(
    groupViewModel: GroupViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = groupViewModel.state.value

    Scaffold(
        topBar = {
            Surface(elevation = 8.dp){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier
                            .padding(20.dp),
                        text = state.group.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(state.isUpdating) CircularProgressIndicator(modifier = Modifier.size(30.dp))
                        if(state.group.ownerId == state.user.id) IconButton(
                            onClick = { navController.navigate("/edit_group/${state.group.id}") }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp),
                                tint = Color.Black,
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = "edit account"
                            )
                        }
                    }
                }
            }
        }
    ) {
        AnimatedVisibility(
            visible = state.updateError.isNotBlank(),
            exit = slideOutVertically(),
            enter = slideInVertically()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    text = state.updateError ?: "??",
                    color = Color.White
                )
            }
        }
    }
}






















