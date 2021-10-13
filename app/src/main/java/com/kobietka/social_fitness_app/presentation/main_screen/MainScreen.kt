package com.kobietka.social_fitness_app.presentation.main_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
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
import com.kobietka.social_fitness_app.presentation.components.StandardTextField


@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = mainViewModel.screenState.value
    val groupName = mainViewModel.groupName.value
    val groupDescription = mainViewModel.groupDescription.value

    Scaffold(
        floatingActionButton = {
            if(!state.isCreatingGroup) FloatingActionButton(
                onClick = mainViewModel::onFabClick,
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "create group"
                )
            }
        },
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
                            text = state.user.nickname,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    IconButton(
                        modifier = Modifier.padding(end = 20.dp),
                        onClick = { navController.navigate(Screen.EditUser.route) }
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            tint = Color.Black,
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "edit account"
                        )
                    }
                }
            }
        }
    ) {
        if(state.isCreatingGroup) Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.End),
                onClick = mainViewModel::onCancelClick
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = "close creating group"
                )
            }
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Create group",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            if(state.error.isNotBlank()) Text(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = state.error,
                color = MaterialTheme.colors.error,
                fontWeight = FontWeight.Bold
            )
            StandardTextField(
                text = groupName.text,
                error = groupName.error,
                label = groupName.label,
                onValueChange = mainViewModel::onGroupNameChange
            )
            StandardTextField(
                text = groupDescription.text,
                error = groupDescription.error,
                label = groupDescription.label,
                onValueChange = mainViewModel::onGroupDescriptionChange
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 13.dp)
                    .height(50.dp),
                onClick = mainViewModel::onCreateGroupClick,
                enabled = !state.isLoading
            ) {
                if(!state.isLoading) Text("Create group")
                else CircularProgressIndicator()
            }
        }
    }

}


































