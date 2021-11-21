package com.kobietka.social_fitness_app.presentation.create_event.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kobietka.social_fitness_app.domain.state.EventTypeState
import com.kobietka.social_fitness_app.presentation.ui.theme.gray


@Composable
fun EventTypeSelector(
    types: List<EventTypeState>,
    selectedType: EventTypeState,
    onIconClick: () -> Unit,
    onTypeClick: (String) -> Unit,
    onDismiss: () -> Unit,
    isExpanded: Boolean,
    error: String
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
                if(error.isNotBlank()){
                    Text(
                        text = error,
                        color = MaterialTheme.colors.error
                    )
                } else {
                    if(selectedType.name == null){
                        Text(text = "Select event type")
                    } else {
                        Column {
                            Text(text = "Event type")
                            Text(
                                text = selectedType.name,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            IconButton(onClick = { onIconClick() }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "select event type"
                )
            }
        }
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = isExpanded,
            onDismissRequest = onDismiss
        ) {
            types.forEach { eventType ->
                DropdownMenuItem(onClick = {
                    onTypeClick(eventType.code!!)
                }) {
                    Text(text = eventType.name!!)
                }
            }
        }
    }

}



















