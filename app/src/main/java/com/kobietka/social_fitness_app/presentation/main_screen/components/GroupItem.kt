package com.kobietka.social_fitness_app.presentation.main_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kobietka.social_fitness_app.domain.model.Group


@Composable
fun GroupItem(group: Group) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Text(
            text = group.name,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = group.description,
            color = Color.Gray,
            fontSize = 18.sp
        )
    }
}