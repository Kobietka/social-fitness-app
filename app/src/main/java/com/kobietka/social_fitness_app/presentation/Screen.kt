package com.kobietka.social_fitness_app.presentation


sealed class Screen(val route: String){
    object Register : Screen("/register")
    object Login : Screen("/login")
    object MainScreen : Screen("/main")
}
