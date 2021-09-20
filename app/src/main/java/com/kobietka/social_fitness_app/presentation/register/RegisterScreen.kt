package com.kobietka.social_fitness_app.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kobietka.social_fitness_app.presentation.components.PasswordTextField
import com.kobietka.social_fitness_app.presentation.components.StandardTextField

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val state = registerViewModel.screenState.value
    val nicknameState = registerViewModel.nickname.value
    val emailState = registerViewModel.email.value
    val passwordState = registerViewModel.password.value
    val repeatPasswordState = registerViewModel.repeatPassword.value

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 50.dp),
            text = "Welcome to",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Text(
            modifier = Modifier
                .padding(bottom = 50.dp),
            text = "Social Fitness App!",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        StandardTextField(
            text = nicknameState.text,
            error = nicknameState.error,
            label = nicknameState.label,
            onValueChange = registerViewModel::onNicknameChange
        )
        StandardTextField(
            text = emailState.text,
            error = emailState.error,
            label = emailState.label,
            onValueChange = registerViewModel::onEmailChange
        )
        PasswordTextField(
            text = passwordState.text,
            error = passwordState.error,
            label = passwordState.label,
            isVisible = passwordState.isVisible,
            onVisibilityChange = registerViewModel::onPasswordVisibilityChange,
            onValueChange = registerViewModel::onPasswordChange
        )
        PasswordTextField(
            text = repeatPasswordState.text,
            error = repeatPasswordState.error,
            label = repeatPasswordState.label,
            isVisible = repeatPasswordState.isVisible,
            onVisibilityChange = registerViewModel::onRepeatPasswordVisibilityChange,
            onValueChange = registerViewModel::onRepeatPasswordChange
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)
                .height(50.dp),
            onClick = registerViewModel::onRegisterClick,
            enabled = !state.isLoading
        ) {
            if(!state.isLoading) Text(text = "Register")
            else CircularProgressIndicator()
        }
        Text(
            modifier = Modifier.padding(top = 30.dp),
            text = "Already have an account?",
            color = Color.Gray
        )
    }
}








