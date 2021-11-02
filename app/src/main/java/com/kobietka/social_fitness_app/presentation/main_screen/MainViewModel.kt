package com.kobietka.social_fitness_app.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.model.GroupValidationResult
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import com.kobietka.social_fitness_app.domain.usecase.auth.LogoutUserUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.*
import com.kobietka.social_fitness_app.domain.usecase.groupmember.JoinGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.main.GetUsersUseCase
import com.kobietka.social_fitness_app.util.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject constructor(
    getUsers: GetUsersUseCase,
    private val logoutUser: LogoutUserUseCase,
    private val validateGroup: ValidateGroup,
    private val createGroup: CreateGroupUseCase,
    private val joinGroup: JoinGroupUseCase,
    getGroups: GetGroupsUseCase,
    getRemoteGroups: GetRemoteGroupsUseCase
) : ViewModel() {

    private val _screenState = mutableStateOf(MainScreenState())
    val screenState: State<MainScreenState> = _screenState

    private val _groupName = mutableStateOf(StandardTextFieldState(label = "Group name"))
    val groupName: State<StandardTextFieldState> = _groupName

    private val _groupDescription = mutableStateOf(StandardTextFieldState(label = "Group description"))
    val groupDescription: State<StandardTextFieldState> = _groupDescription

    private val _code = mutableStateOf(StandardTextFieldState(label = "Invitation code"))
    val code: State<StandardTextFieldState> = _code

    init {
        getUsers().onEach { users ->
            try {
                _screenState.value = _screenState.value.copy(user = users.first())
            } catch (exception: Exception) { }
        }.launchIn(viewModelScope)

        getGroups().onEach { groups ->
            _screenState.value = _screenState.value.copy(groups = groups)
        }.launchIn(viewModelScope)

        getRemoteGroups().onEach { progress ->
            delay(1000)
            when(progress){
                is Progress.Finished -> {
                    delay(1000)
                    _screenState.value = _screenState.value.copy(updatingGroupsMessage = "Saving groups")
                    delay(1000)
                    _screenState.value = _screenState.value.copy(isUpdatingGroups = false)
                }
                is Progress.Loading -> {
                    _screenState.value = _screenState.value.copy(
                        isUpdatingGroups = true,
                        updatingGroupsMessage = "Updating groups"
                    )
                }
                is Progress.Error -> {
                    delay(1000)
                    _screenState.value = _screenState.value.copy(updatingGroupsMessage = progress.message)
                    delay(2000)
                    _screenState.value = _screenState.value.copy(isUpdatingGroups = false)
                }
                is Progress.Unauthorized -> {
                    delay(1000)
                    _screenState.value = _screenState.value.copy(isUpdatingGroups = false)
                    logoutUser()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onGroupDescriptionChange(value: String){
        _groupDescription.value = _groupDescription.value.copy(text = value)
    }

    fun onGroupNameChange(value: String){
        _groupName.value = _groupName.value.copy(text = value)
    }

    fun onCodeChange(value: String){
        _code.value = _code.value.copy(text = value)
    }

    fun onJoinGroupClick(){
        val invitationCode = _code.value.text.trim()
        if(invitationCode.isNotBlank()){
            joinGroup(code = invitationCode).onEach { progress ->
                when(progress){
                    is Progress.Loading -> _screenState.value = _screenState.value.copy(isJoiningGroup = true)
                    is Progress.Finished -> _screenState.value = _screenState.value.copy(
                        isJoiningGroup = false,
                        isCreatingGroup = false
                    )
                    is Progress.Error -> _screenState.value = _screenState.value.copy(
                        isJoiningGroup = false,
                        joinGroupError = progress.message
                    )
                    is Progress.Unauthorized -> {
                        _screenState.value = _screenState.value.copy(isJoiningGroup = false)
                        logoutUser()
                    }
                }
            }.launchIn(viewModelScope)
        } else _code.value = _code.value.copy(error = "This field cannot be blank")
    }

    fun onCreateGroupClick(){
        _groupName.value = _groupName.value.copy(error = "")
        _groupDescription.value = _groupDescription.value.copy(error = "")

        val name = _groupName.value.text.trim()
        val description = _groupDescription.value.text.trim()

        val validationResult = validateGroup(
            groupName = name,
            groupDescription = description
        )

        when(validationResult){
            is GroupValidationResult.Success -> {
                createGroup(
                    name = name,
                    description = description
                ).onEach { progress ->
                    when(progress){
                        is Progress.Finished -> {
                            _screenState.value = _screenState.value.copy(
                                isLoading = false,
                                isCreatingGroup = false
                            )
                        }
                        is Progress.Loading -> {
                            _screenState.value = _screenState.value.copy(isLoading = true)
                        }
                        is Progress.Error -> {
                            _screenState.value = _screenState.value.copy(
                                isLoading = false,
                                error = progress.message
                            )
                        }
                        is Progress.Unauthorized -> {
                            _screenState.value = _screenState.value.copy(isLoading = false)
                            logoutUser()
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is GroupValidationResult.GroupNameBlank -> {
                _groupName.value = _groupName.value.copy(error = "Name cannot be blank")
            }
            is GroupValidationResult.GroupDescriptionBlank -> {
                _groupDescription.value = _groupDescription.value.copy(error = "Description cannot be blank")
            }
        }
    }

    fun onFabClick(){
        _screenState.value = _screenState.value.copy(isCreatingGroup = true)
    }

    fun onCancelClick(){
        _screenState.value = _screenState.value.copy(isCreatingGroup = false)
    }

}


















