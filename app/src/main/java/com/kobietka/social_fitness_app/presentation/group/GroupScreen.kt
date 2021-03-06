package com.kobietka.social_fitness_app.presentation.group

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Details
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
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
import com.kobietka.social_fitness_app.presentation.group.components.EventListItem
import com.kobietka.social_fitness_app.presentation.group.components.PostListItem


@ExperimentalAnimationApi
@Composable
fun GroupScreen(
    groupViewModel: GroupViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = groupViewModel.state.value
    val postListState = rememberLazyListState()
    val eventListState = rememberLazyListState()

    Scaffold(
        topBar = {
            Surface(elevation = 8.dp){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
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
                                if(state.isUserAGroupOwner) IconButton(
                                    onClick = { navController.navigate("/edit_group/${state.group.id}") }
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .size(30.dp),
                                        tint = Color.Black,
                                        imageVector = Icons.Outlined.Edit,
                                        contentDescription = "edit group"
                                    )
                                } else {
                                    IconButton(
                                        onClick = { navController.navigate("/edit_group/${state.group.id}") }
                                    ) {
                                        Icon(
                                            modifier = Modifier
                                                .size(30.dp),
                                            tint = Color.Black,
                                            imageVector = Icons.Outlined.Info,
                                            contentDescription = "group details"
                                        )
                                    }
                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .padding(start = 40.dp, end = 40.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier
                                    .clickable(onClick = groupViewModel::onPostsClick),
                            ) {
                                Text(
                                    modifier = Modifier.padding(20.dp),
                                    text = "POSTS",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 18.sp,
                                    color = if(state.page == GroupPage.POSTS)
                                        MaterialTheme.colors.primary
                                    else Color.Black
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .clickable(onClick = groupViewModel::onEventsClick)
                            ) {
                                Text(
                                    modifier = Modifier.padding(20.dp),
                                    text = "EVENTS",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 18.sp,
                                    color = if(state.page == GroupPage.EVENTS)
                                        MaterialTheme.colors.primary
                                    else Color.Black
                                )
                            }
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = !postListState.isScrollInProgress,
                exit = fadeOut(),
                enter = fadeIn()
            ) {
                FloatingActionButton(
                    onClick = {
                        when(state.page){
                            GroupPage.POSTS -> navController.navigate("/create_post/${state.group.id}")
                            GroupPage.EVENTS -> navController.navigate("/create_event/${state.group.id}")
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "create post"
                    )
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
                    text = state.updateError,
                    color = Color.White
                )
            }
        }
        if(state.isUpdating){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else {
            if(state.page == GroupPage.POSTS) LazyColumn(
                state = postListState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(state.posts.isEmpty()) item {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "No posts",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    )
                }
                else items(state.posts){ post ->
                    PostListItem(
                        post = post,
                        onPostClick = { postId ->
                            navController.navigate("/group/${state.group.id}/post/$postId")
                        }
                    )
                }
            }
            if(state.page == GroupPage.EVENTS) LazyColumn(
                state = eventListState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(state.activeEvents.isEmpty()) item {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "No events",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    )
                }
                else {
                    items(state.activeEvents){ event ->
                        EventListItem(
                            event = event,
                            onEventClick = { eventId ->
                                navController.navigate("/group/${state.group.id}/event/$eventId")
                            }
                        )
                    }
                    item {
                        if(state.archivedEvents.isNotEmpty()) Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Divider(
                                thickness = 2.dp,
                                modifier = Modifier.fillMaxWidth(0.40f)
                            )
                            Icon(
                                imageVector = Icons.Default.Archive,
                                contentDescription = "archived events"
                            )
                            Divider(
                                thickness = 2.dp,
                                modifier = Modifier.fillMaxWidth(0.75f)
                            )
                        }
                    }
                    items(state.archivedEvents) { event ->
                        EventListItem(
                            event = event,
                            onEventClick = { eventId ->
                                navController.navigate("/group/${state.group.id}/event/$eventId")
                            }
                        )
                    }
                }
            }
        }
    }
}






















