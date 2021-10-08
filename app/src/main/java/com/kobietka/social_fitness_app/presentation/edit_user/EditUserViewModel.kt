package com.kobietka.social_fitness_app.presentation.edit_user

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.state.PasswordTextFieldState
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import com.kobietka.social_fitness_app.domain.usecase.main.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class EditUserViewModel
@Inject constructor(
    getUsers: GetUsersUseCase
) : ViewModel() {

    private val _screenState = mutableStateOf(EditUserScreenState())
    val screenState: State<EditUserScreenState> = _screenState

    private val _nickname = mutableStateOf(StandardTextFieldState(label = "Nickname"))
    val nickname: State<StandardTextFieldState> = _nickname

    private val _dataPassword = mutableStateOf(PasswordTextFieldState(label = "Password"))
    val dataPassword: State<PasswordTextFieldState> = _dataPassword

    private val _oldPassword = mutableStateOf(PasswordTextFieldState(label = "Old password"))
    val oldPassword: State<PasswordTextFieldState> = _oldPassword

    private val _newPassword = mutableStateOf(PasswordTextFieldState(label = "New password"))
    val newPassword: State<PasswordTextFieldState> = _newPassword

    init {
        getUsers().onEach { users ->
            try {
                val loggedUser = users.first()
                _screenState.value = _screenState.value.copy(user = loggedUser)
                _nickname.value = _nickname.value.copy(text = loggedUser.nickname)
            } catch (exception: Exception){ }
        }.launchIn(viewModelScope)
    }

    fun onUpdateDataClick(){

    }

    fun onUpdatePasswordClick(onSuccess: () -> Unit){

    }

    fun onLogoutClick(){

    }

    fun onNewPasswordChange(value: String){
        _newPassword.value = _newPassword.value.copy(text = value)
    }

    fun onOldPasswordChange(value: String){
        _oldPassword.value = _oldPassword.value.copy(text = value)
    }

    fun onDataPasswordChange(value: String){
        _dataPassword.value = _dataPassword.value.copy(text = value)
    }

    fun onNicknameChange(value: String){
        _nickname.value = _nickname.value.copy(text = value)
    }

    fun onDataPasswordVisibilityChange(){
        _dataPassword.value = _dataPassword.value.copy(isVisible = !_dataPassword.value.isVisible)
    }

    fun onNewPasswordVisibilityChange(){
        _newPassword.value = _newPassword.value.copy(isVisible = !_newPassword.value.isVisible)
    }

    fun onOldPasswordVisibilityChange(){
        _oldPassword.value = _oldPassword.value.copy(isVisible = !_oldPassword.value.isVisible)
    }

}








































