package com.kobietka.social_fitness_app.presentation.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kobietka.social_fitness_app.presentation.Screen
import com.kobietka.social_fitness_app.presentation.components.StandardTextField
import com.kobietka.social_fitness_app.presentation.main_screen.components.GroupItem


@ExperimentalAnimationApi
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = mainViewModel.screenState.value
    val groupName = mainViewModel.groupName.value
    val groupDescription = mainViewModel.groupDescription.value
    val code = mainViewModel.code.value

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
        AnimatedVisibility(
            visible = state.isUpdatingGroups,
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
                    text = state.updatingGroupsMessage,
                    color = Color.White
                )
            }
        }
        if (state.isUpdatingGroups) Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        } else LazyColumn {
            item {
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
                    Text(
                        modifier = Modifier.padding(20.dp),
                        text = "Or join group",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    if(state.joinGroupError.isNotBlank()){
                        Text(
                            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                            text = state.joinGroupError,
                            color = MaterialTheme.colors.error,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    StandardTextField(
                        text = code.text,
                        error = code.error,
                        label = code.label,
                        onValueChange = mainViewModel::onCodeChange
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 13.dp)
                            .height(50.dp),
                        onClick = mainViewModel::onJoinGroupClick,
                        enabled = !state.isJoiningGroup
                    ) {
                        if(!state.isJoiningGroup) Text("Join")
                        else CircularProgressIndicator()
                    }
                }
            }
            if(state.groups.isEmpty()) item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    text = "No groups",
                    textAlign = TextAlign.Center
                )
            }
            else items(state.groups.size){
                GroupItem(
                    group = state.groups[it],
                    onGroupClick = { groupId ->
                        navController.navigate("/group/$groupId")
                    }
                )
            }
        }
    }

}


































