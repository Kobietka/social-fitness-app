package com.kobietka.social_fitness_app.presentation.post.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kobietka.social_fitness_app.presentation.components.StandardTextField
import com.kobietka.social_fitness_app.presentation.post.CommentState


@ExperimentalAnimationApi
@Composable
fun CommentListItem(
    commentState: CommentState,
    isLoggedUserACommentOwner: Boolean,
    onDeleteCommentClick: (String) -> Unit,
    onExpandCommentClick: (String) -> Unit,
    onEditCommentClick: (String) -> Unit,
    onCommentContentChange: (String, String) -> Unit,
    onCancelCommentEdit: (String) -> Unit,
    onUpdateComment: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { if (!commentState.isEditing) onExpandCommentClick(commentState.comment.id) }
            .padding(bottom = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
        ) {
            Row {
                Text(
                    text = commentState.comment.user.nickname,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = commentState.comment.createdAt,
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray
                )
            }
            if(commentState.isEditing) StandardTextField(
                text = commentState.commentEditValue.text,
                error = commentState.commentEditValue.error,
                label = commentState.commentEditValue.label,
                onValueChange = { value ->
                    onCommentContentChange(commentState.comment.id, value)
                }
            ) else Text(text = commentState.comment.content)
            AnimatedVisibility(visible = commentState.isExpanded && isLoggedUserACommentOwner) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        IconButton(onClick = { onDeleteCommentClick(commentState.comment.id) }) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Default.Delete,
                                contentDescription = "delete comment"
                            )
                        }
                        if(commentState.isEditing) IconButton(
                            onClick = { onCancelCommentEdit(commentState.comment.id) }
                        ) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Default.Cancel,
                                contentDescription = "cancel edit comment"
                            )
                        }
                        else IconButton(onClick = { onEditCommentClick(commentState.comment.id) }) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Default.Edit,
                                contentDescription = "edit comment"
                            )
                        }
                    }
                    if(commentState.isLoading) CircularProgressIndicator()
                    else if(commentState.isEditing && commentState.commentEditValue.text.isNotBlank()) {
                        IconButton(onClick = { onUpdateComment(commentState.comment.id) }) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Default.Send,
                                contentDescription = "update comment"
                            )
                        }
                    }
                }
            }
        }
    }
}