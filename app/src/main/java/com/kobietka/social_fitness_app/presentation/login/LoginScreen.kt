package com.kobietka.social_fitness_app.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kobietka.social_fitness_app.presentation.Screen
import com.kobietka.social_fitness_app.presentation.components.PasswordTextField
import com.kobietka.social_fitness_app.presentation.components.StandardTextField

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = loginViewModel.screenState.value
    val emailState = loginViewModel.email.value
    val passwordState = loginViewModel.password.value

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 50.dp),
            text = "Welcome back to",
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
            text = emailState.text,
            error = emailState.error,
            label = emailState.label,
            onValueChange = loginViewModel::onEmailChange
        )
        PasswordTextField(
            text = passwordState.text,
            error = passwordState.error,
            label = passwordState.label,
            isVisible = passwordState.isVisible,
            onVisibilityChange = loginViewModel::onPasswordVisibilityChange,
            onValueChange = loginViewModel::onPasswordChange
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)
                .height(50.dp),
            onClick = {
                loginViewModel.onLoginClick(
                    onLoginSuccess = {
                        navController.navigate(Screen.MainScreen.route) {
                            popUpTo(Screen.Login.route){
                                inclusive = true
                            }
                        }
                    }
                )
            },
            enabled = !state.isLoading
        ) {
            if (!state.isLoading) Text(text = "Login")
            else CircularProgressIndicator()
        }
        Text(
            modifier = Modifier
                .padding(top = 30.dp)
                .clickable { navController.navigate(Screen.Register.route) },
            text = "Don't have an account?",
            color = Color.Gray
        )
    }
}