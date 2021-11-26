package com.kobietka.social_fitness_app.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.material.datepicker.MaterialDatePicker
import com.kobietka.social_fitness_app.presentation.create_activity.CreateActivityScreen
import com.kobietka.social_fitness_app.presentation.create_event.CreateEventScreen
import com.kobietka.social_fitness_app.presentation.create_post.CreatePostScreen
import com.kobietka.social_fitness_app.presentation.edit_event.EditEventScreen
import com.kobietka.social_fitness_app.presentation.edit_group.EditGroupScreen
import com.kobietka.social_fitness_app.presentation.edit_user.EditUserScreen
import com.kobietka.social_fitness_app.presentation.event.EventScreen
import com.kobietka.social_fitness_app.presentation.group.GroupScreen
import com.kobietka.social_fitness_app.presentation.loading.LoadingScreen
import com.kobietka.social_fitness_app.presentation.login.LoginScreen
import com.kobietka.social_fitness_app.presentation.main_screen.MainScreen
import com.kobietka.social_fitness_app.presentation.post.PostScreen
import com.kobietka.social_fitness_app.presentation.register.RegisterScreen
import com.kobietka.social_fitness_app.presentation.ui.theme.SocialfitnessappTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    @ExperimentalTime
    @ExperimentalAnimationApi
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

                    composable(
                        route = Screen.Group.route,
                        arguments = listOf(navArgument(name = "groupId"){ type = NavType.StringType })
                    ){
                        GroupScreen(navController = navController)
                    }

                    composable(
                        route = Screen.Post.route,
                        arguments = listOf(
                            navArgument(name = "postId"){ type = NavType.StringType },
                            navArgument(name = "groupId"){ type = NavType.StringType }
                        )
                    ){
                        PostScreen(onPostDelete = { onBackPressed() })
                    }

                    composable(
                        route = Screen.CreatePost.route,
                        arguments = listOf(navArgument(name = "groupId"){ type = NavType.StringType })
                    ){
                        CreatePostScreen(onSuccessPostCreation = { onBackPressed() })
                    }

                    composable(
                        route = Screen.EditGroup.route,
                        arguments = listOf(navArgument(name = "groupId"){ type = NavType.StringType })
                    ){
                        EditGroupScreen()
                    }

                    composable(
                        route = Screen.CreateEvent.route,
                        arguments = listOf(navArgument(name = "groupId"){ type = NavType.StringType })
                    ){
                        CreateEventScreen(
                            onStartDateClick = { onFinish ->
                                openDatePicker(
                                    onSuccess = { onFinish(it) },
                                    onDismiss = { onFinish(it) }
                                )
                            },
                            onEndDateClick = { onFinish ->
                                openDatePicker(
                                    onSuccess = { onFinish(it) },
                                    onDismiss = { onFinish(it) }
                                )
                            },
                            onSuccessEventCreation = { onBackPressed() }
                        )
                    }

                    composable(
                        route = Screen.Event.route,
                        arguments = listOf(
                            navArgument(name = "eventId"){ type = NavType.StringType },
                            navArgument(name = "groupId"){ type = NavType.StringType }
                        )
                    ){
                        EventScreen(navController = navController)
                    }

                    composable(
                        route = Screen.EditEvent.route,
                        arguments = listOf(
                            navArgument(name = "eventId"){ type = NavType.StringType },
                            navArgument(name = "groupId"){ type = NavType.StringType }
                        )
                    ){
                        EditEventScreen(onSuccessfulEventEdit = { onBackPressed() })
                    }

                    composable(
                        route = Screen.CreateActivity.route,
                        arguments = listOf(
                            navArgument(name = "eventId"){ type = NavType.StringType }
                        )
                    ){
                        CreateActivityScreen(onSuccess = { onBackPressed() })
                    }
                }
            }
        }
    }

    private fun openDatePicker(onSuccess: (Long) -> Unit, onDismiss: (Long?) -> Unit) {
        val picker = MaterialDatePicker.Builder.datePicker().build()
        picker.show(this.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            onSuccess(it)
        }
        picker.addOnDismissListener {
            onDismiss(null)
        }
    }

}

















