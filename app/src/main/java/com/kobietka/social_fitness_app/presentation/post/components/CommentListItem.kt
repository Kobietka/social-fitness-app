package com.kobietka.social_fitness_app.presentation.post.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kobietka.social_fitness_app.domain.model.Comment


@ExperimentalAnimationApi
@Composable
fun CommentListItem(
    comment: Comment,
    isLoggedUserACommentOwner: Boolean,
    onDeleteCommentClick: (String) -> Unit,
) {
    val isExpanded = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded.value = !isExpanded.value }
            .padding(bottom = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
        ) {
            Row {
                Text(
                    text = comment.user.nickname,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = comment.createdAt,
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray
                )
            }
            Text(text = comment.content)
            AnimatedVisibility(visible = isExpanded.value && isLoggedUserACommentOwner) {
                IconButton(onClick = { onDeleteCommentClick(comment.id) }) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete comment"
                    )
                }
            }
        }
    }
}