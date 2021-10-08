package com.kobietka.social_fitness_app.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.usecase.main.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject constructor(
    getUsers: GetUsersUseCase
) : ViewModel() {

    private val _screenState = mutableStateOf(MainScreenState())
    val screenState: State<MainScreenState> = _screenState

    init {
        getUsers().onEach { users ->
            try {
                _screenState.value = _screenState.value.copy(user = users.first())
            } catch (exception: Exception) { }
        }.launchIn(viewModelScope)
    }

}

