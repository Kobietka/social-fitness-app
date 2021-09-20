package com.kobietka.social_fitness_app.presentation.register

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.state.PasswordTextFieldState
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import com.kobietka.social_fitness_app.domain.usecase.auth.RegisterUserUseCase
import com.kobietka.social_fitness_app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _screenState = mutableStateOf(RegisterScreenState())
    val screenState: State<RegisterScreenState> = _screenState

    private val _nickname = mutableStateOf(StandardTextFieldState(label = "Nickname"))
    val nickname: State<StandardTextFieldState> = _nickname

    private val _email = mutableStateOf(StandardTextFieldState(label = "Email"))
    val email: State<StandardTextFieldState> = _email

    private val _password = mutableStateOf(PasswordTextFieldState(label = "Password"))
    val password: State<PasswordTextFieldState> = _password

    private val _repeatPassword = mutableStateOf(PasswordTextFieldState(label = "Repeat Password"))
    val repeatPassword: State<PasswordTextFieldState> = _repeatPassword

    fun onRegisterClick(){
        _nickname.value = nickname.value.copy(error = "")
        _email.value = email.value.copy(error = "")
        _password.value = password.value.copy(error = "")
        _repeatPassword.value = repeatPassword.value.copy(error = "")
        when(validate()){
            is ValidationResult.Success -> {
                registerUserUseCase(
                    nickname = nickname.value.text,
                    email = email.value.text,
                    password = password.value.text
                ).onEach { result ->
                    when(result){
                        is Resource.Loading -> { _screenState.value = screenState.value.copy(isLoading = true) }
                        is Resource.Success -> {
                            _screenState.value = screenState.value.copy(isLoading = false)
                            Log.e("Success", "registration was successful")
                        }
                        is Resource.Error -> {
                            Log.e("Error", result.message ?: "")
                            _screenState.value = screenState.value.copy(isLoading = false)
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is ValidationResult.EmailNotValid -> {
                _email.value = email.value.copy(error = "This email address is not valid")
            }
            is ValidationResult.NicknameTooShort -> {
                _nickname.value = nickname.value.copy(error = "Minimum nickname length is 4 characters")
            }
            is ValidationResult.PasswordTooShort -> {
                _password.value = password.value.copy(error = "Minimum password length is 8 characters")
            }
            is ValidationResult.PasswordsAreNotTheSame -> {
                _password.value = password.value.copy(error = "Passwords are not the same")
                _repeatPassword.value = _repeatPassword.value.copy(error = "Passwords are not the same")
            }
        }
    }

    private fun validate(): ValidationResult {
        if(nickname.value.text.length < 4) return ValidationResult.NicknameTooShort
        if(!isValidEmail(email.value.text)) return ValidationResult.EmailNotValid
        if(password.value.text.length < 8) return ValidationResult.PasswordTooShort
        if(password.value.text != repeatPassword.value.text) return ValidationResult.PasswordsAreNotTheSame

        return ValidationResult.Success
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

    private fun isValidEmail(email: String) = Pattern.compile( "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+", Pattern.CASE_INSENSITIVE).matcher(email).find()

}

sealed class ValidationResult {
    object Success : ValidationResult()
    object EmailNotValid : ValidationResult()
    object PasswordsAreNotTheSame : ValidationResult()
    object NicknameTooShort : ValidationResult()
    object PasswordTooShort : ValidationResult()
}












