package com.kobietka.social_fitness_app.presentation.group

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
import com.kobietka.social_fitness_app.presentation.Screen


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
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        Text(
                            text = state.group.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    IconButton(
                        modifier = Modifier.padding(end = 20.dp),
                        onClick = { /* navigate to edit group */ }
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
    ) {

    }
}