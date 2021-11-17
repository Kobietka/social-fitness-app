package com.kobietka.social_fitness_app.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun PasswordTextField(
    text: String,
    error: String,
    label: String,
    isVisible: Boolean,
    onVisibilityChange: () -> Unit,
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
            singleLine = true,
            trailingIcon = {
                if(isVisible) IconButton(onClick = onVisibilityChange) {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "password visibility off"
                    )
                }
                else IconButton(onClick = onVisibilityChange) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "password visibility"
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if(isVisible) VisualTransformation.None else PasswordVisualTransformation()
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