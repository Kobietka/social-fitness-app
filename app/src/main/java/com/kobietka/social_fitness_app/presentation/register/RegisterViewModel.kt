package com.kobietka.social_fitness_app.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kobietka.social_fitness_app.domain.state.PasswordTextFieldState
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(

) : ViewModel() {

    private val _nickname = mutableStateOf(StandardTextFieldState(label = "Nickname"))
    val nickname: State<StandardTextFieldState> = _nickname

    private val _email = mutableStateOf(StandardTextFieldState(label = "Email"))
    val email: State<StandardTextFieldState> = _email

    private val _password = mutableStateOf(PasswordTextFieldState(label = "Password"))
    val password: State<PasswordTextFieldState> = _password

    private val _repeatPassword = mutableStateOf(PasswordTextFieldState(label = "Repeat Password"))
    val repeatPassword: State<PasswordTextFieldState> = _repeatPassword

    fun onRegisterClick(){

    }

    fun onNicknameChange(value: String){
        _nickname.value = _nickname.value.copy(text = value)
    }

    fun onEmailChange(value: String){
        _email.value = _email.value.copy(text = value)
    }

    fun onPasswordChange(value: String){
        _password.value = _password.value.copy(text = value)
    }

    fun onPasswordVisibilityChange(){
        _password.value = _password.value.copy(isVisible = !password.value.isVisible)
    }

    fun onRepeatPasswordChange(value: String){
        _repeatPassword.value = _repeatPassword.value.copy(text = value)
    }

    fun onRepeatPasswordVisibilityChange(){
        _repeatPassword.value = _repeatPassword.value.copy(isVisible = !repeatPassword.value.isVisible)
    }
}












