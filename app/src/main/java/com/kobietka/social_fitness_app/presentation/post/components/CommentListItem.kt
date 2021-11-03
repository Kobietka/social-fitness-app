package com.kobietka.social_fitness_app.presentation.post.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kobietka.social_fitness_app.domain.model.Comment


@Composable
fun CommentListItem(
    comment: Comment
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(start = 20.dp , end = 20.dp, top = 10.dp)
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
        }
    }
}