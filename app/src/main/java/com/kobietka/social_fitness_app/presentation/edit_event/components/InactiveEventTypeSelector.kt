package com.kobietka.social_fitness_app.presentation.edit_event.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kobietka.social_fitness_app.domain.state.EventTypeState
import com.kobietka.social_fitness_app.presentation.ui.theme.gray


@Composable
fun InactiveEventTypeSelector(
    selectedType: EventTypeState,
) {
    Column(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .background(gray)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                selectedType.name?.let { eventTypeName ->
                    Text(text = "Event type")
                    Text(
                        text = eventTypeName,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

}