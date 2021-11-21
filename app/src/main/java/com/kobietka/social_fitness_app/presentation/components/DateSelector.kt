package com.kobietka.social_fitness_app.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kobietka.social_fitness_app.presentation.ui.theme.gray


@Composable
fun DateSelector(
    dateText: String,
    dateType: String,
    onIconClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp)
            .background(gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                if(dateText.isBlank()){
                    Text(text = "Select ${dateType.lowercase()} date")
                } else {
                    Column {
                        Text(text = "$dateType date")
                        Text(
                            text = dateText,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            IconButton(onClick = { onIconClick() }) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "calendar"
                )
            }
        }
    }
}



















