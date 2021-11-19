package com.kobietka.social_fitness_app.presentation.group.components

import androidx.compose.foundation.clickable
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
import com.kobietka.social_fitness_app.domain.model.Event



@Composable
fun EventListItem(
    event: Event,
    onEventClick: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEventClick(event.id) }
            .padding(bottom = 6.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = event.name,
                fontWeight = FontWeight.Bold
            )
            Row {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = event.startDate,
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray
                )
                Text(text = ">")
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = event.endDate,
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray
                )
            }
            Text(text = event.description)

        }
    }
}