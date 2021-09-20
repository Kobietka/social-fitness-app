package com.kobietka.social_fitness_app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.kobietka.social_fitness_app.presentation.register.RegisterScreen
import com.kobietka.social_fitness_app.presentation.theme.SocialfitnessappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialfitnessappTheme {
                RegisterScreen()
            }
        }
    }
}


