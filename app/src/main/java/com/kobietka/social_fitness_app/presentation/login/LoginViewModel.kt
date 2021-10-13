package com.kobietka.social_fitness_app.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.state.PasswordTextFieldState
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import com.kobietka.social_fitness_app.domain.usecase.auth.InsertUserCredentialsUseCase
import com.kobietka.social_fitness_app.domain.usecase.auth.LoginUserUseCase
import com.kobietka.social_fitness_app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val insertUserCredentialsUseCase: InsertUserCredentialsUseCase
) : ViewModel() {

    private val _screenState = mutableStateOf(LoginScreenState())
    val screenState: State<LoginScreenState> = _screenState

    private val _email = mutableStateOf(StandardTextFieldState(label = "Email"))
    val email: State<StandardTextFieldState> = _email

    private val _password = mutableStateOf(PasswordTextFieldState(label = "Password"))
    val password: State<PasswordTextFieldState> = _password

    fun onLoginClick(onLoginSuccess: () -> Unit){
        _screenState.value = _screenState.value.copy(error = "")
        loginUserUseCase(
            email = email.value.text.trim(),
            password = password.value.text.trim()
        ).onEach { resource ->
            when(resource){
                is Resource.Success -> {
                    resource.data?.let {
                        _screenState.value = screenState.value.copy(isLoading = false)
                        insertUserCredentialsUseCase(
                            token = it.token,
                            id = it.id,
                            nickname = it.nickname,
                            email = it.email
                        )
                        onLoginSuccess()
                    }
                }
                is Resource.Loading -> {
                    _screenState.value = screenState.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _screenState.value = screenState.value.copy(isLoading = false)
                    resource.message?.let { message ->
                        _screenState.value = _screenState.value.copy(error = message)
                    }
                }
                else -> { }
            }
        }.launchIn(viewModelScope)
    }

    fun onEmailChange(value: String){
        _email.value = email.value.copy(text = value)
    }

    fun onPasswordChange(value: String){
        _password.value = password.value.copy(text = value)
    }

    fun onPasswordVisibilityChange(){
        _password.value = password.value.copy(isVisible = !password.value.isVisible)
    }

}