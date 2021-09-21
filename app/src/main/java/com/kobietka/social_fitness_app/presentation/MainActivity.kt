package com.kobietka.social_fitness_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kobietka.social_fitness_app.presentation.login.LoginScreen
import com.kobietka.social_fitness_app.presentation.register.RegisterScreen
import com.kobietka.social_fitness_app.presentation.theme.SocialfitnessappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SocialfitnessappTheme {
                NavHost(navController = navController, startDestination = Screen.Login.route){
                    composable(route = Screen.Login.route){
                        LoginScreen(navController = navController)
                    }

                    composable(route = Screen.Register.route){
                        RegisterScreen(navController = navController)
                    }

                    composable(route = Screen.MainScreen.route){

                    }
                }
            }
        }
    }
}


