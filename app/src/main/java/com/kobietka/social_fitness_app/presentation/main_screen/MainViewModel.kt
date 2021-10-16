package com.kobietka.social_fitness_app.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.model.CreateGroupValidationResult
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import com.kobietka.social_fitness_app.domain.usecase.auth.LogoutUserUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.*
import com.kobietka.social_fitness_app.domain.usecase.main.GetUsersUseCase
import com.kobietka.social_fitness_app.util.Resource
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
    private val validateCreateGroup: ValidateCreateGroup,
    private val createGroup: CreateGroupUseCase,
    private val insertGroupData: InsertGroupDataUseCase,
    getGroups: GetGroupsUseCase,
    getRemoteGroups: GetRemoteGroupsUseCase
) : ViewModel() {

    private val _screenState = mutableStateOf(MainScreenState())
    val screenState: State<MainScreenState> = _screenState

    private val _groupName = mutableStateOf(StandardTextFieldState(label = "Group name"))
    val groupName: State<StandardTextFieldState> = _groupName

    private val _groupDescription = mutableStateOf(StandardTextFieldState(label = "Group description"))
    val groupDescription: State<StandardTextFieldState> = _groupDescription

    init {
        getUsers().onEach { users ->
            try {
                _screenState.value = _screenState.value.copy(user = users.first())
            } catch (exception: Exception) { }
        }.launchIn(viewModelScope)

        getGroups().onEach { groups ->
            _screenState.value = _screenState.value.copy(groups = groups)
        }.launchIn(viewModelScope)

        getRemoteGroups().onEach { resource ->
            delay(2000)
            when(resource){
                is Resource.Success -> {
                    resource.data?.let { groupResponses ->
                        delay(1000)
                        _screenState.value = _screenState.value.copy(updatingGroupsMessage = "Saving groups")
                        groupResponses.forEach { groupResponse ->
                            insertGroupData(
                                groupId = groupResponse.id,
                                groupName = groupResponse.name,
                                groupDescription = groupResponse.description,
                                groupOwner = groupResponse.owner,
                                invitation = groupResponse.invitation,
                                groupMembers = groupResponse.members
                            )
                        }
                        delay(1000)
                        _screenState.value = _screenState.value.copy(updatingGroupsMessage = "Update successful")
                        delay(2000)
                        _screenState.value = _screenState.value.copy(isUpdatingGroups = false)
                    }
                }
                is Resource.Loading -> {
                    _screenState.value = _screenState.value.copy(
                        isUpdatingGroups = true,
                        updatingGroupsMessage = "Updating groups"
                    )
                }
                is Resource.Error -> {
                    delay(1000)
                    resource.message?.let { message ->
                        _screenState.value = _screenState.value.copy(updatingGroupsMessage = message)
                    }
                    delay(2000)
                    _screenState.value = _screenState.value.copy(isUpdatingGroups = false)
                }
                is Resource.Unauthorized -> {
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

    fun onCreateGroupClick(){
        _groupName.value = _groupName.value.copy(error = "")
        _groupDescription.value = _groupDescription.value.copy(error = "")

        val name = _groupName.value.text.trim()
        val description = _groupDescription.value.text.trim()

        val validationResult = validateCreateGroup(
            groupName = name,
            groupDescription = description
        )

        when(validationResult){
            is CreateGroupValidationResult.Success -> {
                createGroup(
                    name = name,
                    description = description
                ).onEach { resource ->
                    when(resource){
                        is Resource.Success -> {
                            _screenState.value = _screenState.value.copy(
                                isLoading = false,
                                isCreatingGroup = false
                            )
                            resource.data?.let { response ->
                                insertGroupData(
                                    groupId = response.id,
                                    groupName = response.name,
                                    groupDescription = response.description,
                                    groupOwner = response.owner,
                                    invitation = response.invitation,
                                    groupMembers = response.members
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _screenState.value = _screenState.value.copy(isLoading = true)
                        }
                        is Resource.Error -> {
                            _screenState.value = _screenState.value.copy(isLoading = false)
                            resource.message?.let { message ->
                                _screenState.value = _screenState.value.copy(error = message)
                            }
                        }
                        is Resource.Unauthorized -> {
                            _screenState.value = _screenState.value.copy(isLoading = false)
                            logoutUser()
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is CreateGroupValidationResult.GroupNameBlank -> {
                _groupName.value = _groupName.value.copy(error = "Name cannot be blank")
            }
            is CreateGroupValidationResult.GroupDescriptionBlank -> {
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


















