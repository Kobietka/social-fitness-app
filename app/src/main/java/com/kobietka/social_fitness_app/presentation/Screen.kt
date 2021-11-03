package com.kobietka.social_fitness_app.presentation


sealed class Screen(val route: String){
    object Register : Screen("/register")
    object Login : Screen("/login")
    object MainScreen : Screen("/main")
    object Group : Screen("/group/{groupId}")
    object EditGroup : Screen("/edit_group/{groupId}")
    object EditUser : Screen("/edit_user")
    object Loading : Screen("/loading")
    object Post : Screen("/group/{groupId}/post/{postId}")
    object CreatePost : Screen("/create_post/{groupId}")
}
