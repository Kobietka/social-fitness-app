package com.kobietka.social_fitness_app.presentation.post

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kobietka.social_fitness_app.presentation.components.MultilineTextField
import com.kobietka.social_fitness_app.presentation.components.StandardTextField
import com.kobietka.social_fitness_app.presentation.post.components.CommentListItem


@ExperimentalAnimationApi
@Composable
fun PostScreen(
    postViewModel: PostViewModel = hiltViewModel(),
    onPostDelete: () -> Unit
) {
    val state = postViewModel.state.value
    val comment = postViewModel.comment.value
    val postContent = postViewModel.postContent.value

    Column {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                if(state.post.user.userId == state.loggedUser.userId){
                    Column {
                        Row(
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            if(state.isDeletePostLoading) CircularProgressIndicator()
                            else IconButton(
                                onClick = {
                                    postViewModel.onDeletePostClick { onPostDelete() }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    modifier = Modifier.size(30.dp),
                                    contentDescription = "delete post"
                                )
                            }
                            if(state.isEditingPost) IconButton(
                                onClick = postViewModel::onCancelEditPostClick
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    modifier = Modifier.size(30.dp),
                                    contentDescription = "cancel edit post"
                                )
                            }
                            else IconButton(
                                onClick = postViewModel::onEditPostClick
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    modifier = Modifier.size(30.dp),
                                    contentDescription = "edit post"
                                )
                            }
                        }
                        Divider(
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                    }
                }
                Row {
                    Text(
                        text = state.post.user.nickname,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = state.post.createdAt,
                        fontStyle = FontStyle.Italic,
                        color = Color.LightGray
                    )
                }
                if(state.isEditingPost) Column(
                    horizontalAlignment = Alignment.End
                ) {
                    StandardTextField(
                        text = postContent.text,
                        error = postContent.error,
                        label = postContent.label,
                        onValueChange = postViewModel::onPostContentChanged
                    )
                    if(state.isEditingPostLoading) CircularProgressIndicator()
                    else IconButton(
                        onClick = postViewModel::onSendEditPostClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            modifier = Modifier.size(30.dp),
                            contentDescription = "send edit post"
                        )
                    }
                } else Text(text = state.post.content)
            }
        }
        AnimatedVisibility(
            visible = state.loadingError.isNotBlank(),
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
                    text = state.loadingError,
                    color = Color.White
                )
            }
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            if(state.creatingCommentError.isNotBlank()) Text(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                text = state.creatingCommentError,
                color = MaterialTheme.colors.error,
                fontWeight = FontWeight.Bold
            )
            MultilineTextField(
                text = comment.text,
                error = comment.error,
                label = comment.label,
                maxLines = 5,
                onValueChange = postViewModel::onCommentChanged
            )
            if(state.isCreatingComment) CircularProgressIndicator()
            else IconButton(onClick = postViewModel::onSendCommentClick) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "send comment")
            }
        }
        Divider(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            color = Color.Gray
        )
        if(state.isLoading) Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        } else LazyColumn {
            items(state.comments){ comment ->
                CommentListItem(
                    comment = comment,
                    isLoggedUserACommentOwner = comment.user.userId == state.loggedUser.userId,
                    onDeleteCommentClick = postViewModel::onDeleteCommentClick
                )
            }
        }
    }
}













