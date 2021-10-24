package com.kobietka.social_fitness_app.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.model.RegisterValidationResult
import com.kobietka.social_fitness_app.domain.state.PasswordTextFieldState
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import com.kobietka.social_fitness_app.domain.usecase.auth.RegisterUserUseCase
import com.kobietka.social_fitness_app.domain.usecase.auth.ValidateRegisterUseCase
import com.kobietka.social_fitness_app.util.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val validateRegister: ValidateRegisterUseCase
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
        _screenState.value = _screenState.value.copy(error = "")

        val nickname = _nickname.value.text.trim()
        val email = _email.value.text.trim()
        val password = _password.value.text.trim()
        val repeatPassword = _repeatPassword.value.text.trim()

        val validationResult = validateRegister(
            nickname = nickname,
            email = email,
            password = password,
            repeatPassword = repeatPassword
        )

        when(validationResult){
            is RegisterValidationResult.Success -> {
                registerUserUseCase(
                    nickname = nickname,
                    email = email,
                    password = password
                ).onEach { progress ->
                    when(progress){
                        is Progress.Loading -> {
                            _screenState.value = screenState.value.copy(isLoading = true)
                        }
                        is Progress.Finished -> {
                            _screenState.value = screenState.value.copy(isLoading = false)
                            _screenState.value = screenState.value.copy(isRegisterSuccessful = true)
                        }
                        is Progress.Error -> {
                            _screenState.value = screenState.value.copy(
                                isLoading = false,
                                error = progress.message
                            )
                        }
                        is Progress.Unauthorized -> {
                            _screenState.value = screenState.value.copy(isLoading = false)
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is RegisterValidationResult.EmailNotValid -> {
                _email.value = _email.value.copy(error = "This email address is not valid")
            }
            is RegisterValidationResult.NicknameTooShort -> {
                _nickname.value = _nickname.value.copy(error = "Minimum nickname length is 4 characters")
            }
            is RegisterValidationResult.PasswordTooShort -> {
                _password.value = _password.value.copy(error = "Minimum password length is 8 characters")
            }
            is RegisterValidationResult.PasswordsAreNotTheSame -> {
                _password.value = _password.value.copy(error = "Passwords are not the same")
                _repeatPassword.value = _repeatPassword.value.copy(error = "Passwords are not the same")
            }
        }
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

fun isValidEmail(email: String) = Pattern.compile( "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+", Pattern.CASE_INSENSITIVE).matcher(email).find()












