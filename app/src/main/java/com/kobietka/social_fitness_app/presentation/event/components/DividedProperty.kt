package com.kobietka.social_fitness_app.presentation.event.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DividedProperty(
    name: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.padding(bottom = 5.dp),
            text = name,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Divider()
        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = value,
            fontSize = 16.sp
        )
    }
}