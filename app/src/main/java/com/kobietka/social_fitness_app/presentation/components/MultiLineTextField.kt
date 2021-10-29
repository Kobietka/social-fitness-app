package com.kobietka.social_fitness_app.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp


@Composable
fun MultilineTextField(
    text: String,
    error: String,
    label: String,
    maxLines: Int,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 3.dp, bottom = 3.dp)) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("$label Field"),
            value = text,
            label = { Text(text = label) },
            onValueChange = onValueChange,
            isError = error.isNotBlank(),
            maxLines = maxLines
        )
        if(error.isNotBlank()){
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .testTag("$label Error"),
                text = error,
                color = MaterialTheme.colors.error
            )
        }
    }
}