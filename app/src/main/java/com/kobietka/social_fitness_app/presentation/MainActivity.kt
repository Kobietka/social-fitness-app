package com.kobietka.social_fitness_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kobietka.social_fitness_app.presentation.edit_user.EditUserScreen
import com.kobietka.social_fitness_app.presentation.loading.LoadingScreen
import com.kobietka.social_fitness_app.presentation.login.LoginScreen
import com.kobietka.social_fitness_app.presentation.main_screen.MainScreen
import com.kobietka.social_fitness_app.presentation.register.RegisterScreen
import com.kobietka.social_fitness_app.presentation.theme.SocialfitnessappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val startingScreen = mainActivityViewModel.startingScreen.value
            SocialfitnessappTheme {
                NavHost(navController = navController, startDestination = startingScreen){
                    composable(route = Screen.Loading.route){
                        LoadingScreen()
                    }

                    composable(route = Screen.Login.route){
                        LoginScreen(navController = navController)
                    }

                    composable(route = Screen.Register.route){
                        RegisterScreen(navController = navController)
                    }

                    composable(route = Screen.MainScreen.route){
                        MainScreen(navController = navController)
                    }

                    composable(route = Screen.EditUser.route){
                        EditUserScreen(navController = navController)
                    }
                }
            }
        }
    }
}

















