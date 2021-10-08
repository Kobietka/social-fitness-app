package com.kobietka.social_fitness_app.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.usecase.main.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel
@Inject constructor(
    getUsers: GetUsersUseCase
) : ViewModel() {

    private val _startingScreen = mutableStateOf(Screen.Loading.route)
    val startingScreen: State<String> = _startingScreen

    init {
        getUsers().onEach { users ->
            if(users.isEmpty()) _startingScreen.value = Screen.Login.route
            else _startingScreen.value = Screen.MainScreen.route
        }.launchIn(viewModelScope)
    }

}