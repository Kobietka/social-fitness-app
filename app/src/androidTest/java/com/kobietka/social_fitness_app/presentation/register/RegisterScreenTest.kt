package com.kobietka.social_fitness_app.presentation.register

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kobietka.social_fitness_app.di.DatabaseModule
import com.kobietka.social_fitness_app.presentation.MainActivity
import com.kobietka.social_fitness_app.presentation.Screen
import com.kobietka.social_fitness_app.presentation.theme.SocialfitnessappTheme
import com.kobietka.social_fitness_app.util.TestTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
class RegisterScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() = runBlocking {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            SocialfitnessappTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Register.route
                ) {
                    composable(route = Screen.Register.route){
                        RegisterScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun registerInvalidEmail_ErrorVisible(){
        runBlocking { delay(1000) }
        with(composeRule){
            onNodeWithTag("Nickname Field")
                .performTextInput("nickname")
            onNodeWithTag("Email Field")
                .performTextInput("example")
            onNodeWithTag("Password Field")
                .performTextInput("12345678")
            onNodeWithTag("Repeat Password Field")
                .performTextInput("12345678")
            onNodeWithTag(TestTag.REGISTER_BUTTON)
                .performClick()
            onNodeWithTag("Email Error")
                .assertIsDisplayed()
        }
    }

}






















