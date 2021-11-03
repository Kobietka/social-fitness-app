package com.kobietka.social_fitness_app.presentation.create_post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kobietka.social_fitness_app.presentation.components.MultilineTextField


@Composable
fun CreatePostScreen(
    createPostViewModel: CreatePostViewModel = hiltViewModel(),
    onSuccessPostCreation: () -> Unit
) {
    val state = createPostViewModel.state.value
    val postContent = createPostViewModel.postContent.value

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        Text(
            fontStyle = FontStyle.Italic,
            text = "Creating post for",
            color = Color.Gray,
            fontSize = 20.sp
        )
        Text(
            text = state.group.name,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        MultilineTextField(
            text = postContent.text,
            error = postContent.error,
            label = postContent.label,
            maxLines = 10,
            onValueChange = createPostViewModel::onPostContentChange
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)
                .height(50.dp),
            onClick = {
                createPostViewModel.onCreatePostClick {
                    onSuccessPostCreation()
                }
            },
            enabled = !state.isCreatingPost
        ) {
            if(!state.isCreatingPost) Text("Create post")
            else CircularProgressIndicator()
        }
    }
}


















