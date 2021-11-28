package com.kobietka.social_fitness_app.presentation.edit_group

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.model.GroupValidationResult
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import com.kobietka.social_fitness_app.domain.usecase.auth.LogoutUserUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.*
import com.kobietka.social_fitness_app.domain.usecase.groupmember.KickGroupMemberUseCase
import com.kobietka.social_fitness_app.domain.usecase.invitation.CreateInvitationUseCase
import com.kobietka.social_fitness_app.domain.usecase.invitation.DeleteInvitationUseCase
import com.kobietka.social_fitness_app.domain.usecase.invitation.GetInvitationUseCase
import com.kobietka.social_fitness_app.domain.usecase.main.GetUsersUseCase
import com.kobietka.social_fitness_app.util.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class EditGroupViewModel
@Inject constructor(
    handle: SavedStateHandle,
    private val getGroup: GetGroupUseCase,
    private val editGroupUseCase: EditGroupUseCase,
    private val validateGroup: ValidateGroup,
    private val logoutUser: LogoutUserUseCase,
    private val getInvitation: GetInvitationUseCase,
    private val createInvitation: CreateInvitationUseCase,
    private val deleteInvitation: DeleteInvitationUseCase,
    private val getGroupMembers: GetGroupMembersUseCase,
    private val kickGroupMember: KickGroupMemberUseCase,
    private val getUsers: GetUsersUseCase,
    private val deleteGroup: DeleteGroupUseCase
) : ViewModel() {

    private val _state = mutableStateOf(EditGroupState())
    val state: State<EditGroupState> = _state

    private val _groupName = mutableStateOf(StandardTextFieldState(label = "Group name"))
    val groupName: State<StandardTextFieldState> = _groupName

    private val _groupDescription = mutableStateOf(StandardTextFieldState(label = "Group description"))
    val groupDescription: State<StandardTextFieldState> = _groupDescription

    private val _deleteGroupName = mutableStateOf(StandardTextFieldState(label = "Group name"))
    val deleteGroupName: State<StandardTextFieldState> = _deleteGroupName

    init {
        handle.get<String>("groupId")?.let { groupId ->
            getGroup(groupId = groupId).onEach { group ->
                group?.let {
                    _state.value = _state.value.copy(group = group)
                    _groupName.value = _groupName.value.copy(text = group.name)
                    _groupDescription.value = _groupDescription.value.copy(text = group.description)
                    getUsers().onEach { users->
                        try {
                            _state.value = _state.value.copy(
                                isUserAGroupOwner = group.ownerId == users.first().id
                            )
                        } catch (exception: Exception){ }
                    }.launchIn(viewModelScope)
                }
            }.launchIn(viewModelScope)
            getInvitation(groupId = groupId).onEach { invitation ->
                _state.value = _state.value.copy(invitation = invitation)
            }.launchIn(viewModelScope)
            getGroupMembers(groupId = groupId).onEach { groupMembers ->
                _state.value = _state.value.copy(
                    groupMembers = groupMembers.filter { it.userId != state.value.group.ownerId }
                )
            }.launchIn(viewModelScope)
        }
    }

    fun onDeleteGroupClick(onSuccess: () -> Unit){
        val deleteGroupName = deleteGroupName.value.text.trim()
        if(deleteGroupName == state.value.group.name){
            deleteGroup(groupId = state.value.group.id).onEach { progress ->
                when(progress){
                    is Progress.Loading -> _state.value = _state.value.copy(isDeletingGroup = true)
                    is Progress.Finished -> {
                        _state.value = _state.value.copy(isDeletingGroup = false)
                        onSuccess()
                    }
                    is Progress.Unauthorized -> {
                        _state.value = _state.value.copy(isDeletingGroup = false)
                        logoutUser()
                    }
                    else -> { }
                }
            }.launchIn(viewModelScope)
        } else {
            _deleteGroupName.value = _deleteGroupName.value.copy(error = "Please enter a correct group name")
        }
    }

    fun onKickGroupMemberClick(memberId: String){
        kickGroupMember(memberId = memberId).onEach { progress ->
            when(progress){
                is Progress.Unauthorized -> logoutUser()
                else -> { }
            }
        }.launchIn(viewModelScope)
    }

    fun onEditGroupClick(){
        _groupName.value = _groupName.value.copy(error = "")
        _groupDescription.value = _groupDescription.value.copy(error = "")
        _state.value = _state.value.copy(
            updateMessage = "",
            updatingGroupError = ""
        )
        val name = groupName.value.text.trim()
        val description = groupDescription.value.text.trim()

        val validationResult = validateGroup(
            groupName = name,
            groupDescription = description
        )

        when(validationResult){
            is GroupValidationResult.Success -> {
                editGroupUseCase(
                    groupId = state.value.group.id,
                    groupName = name,
                    groupDescription = description
                ).onEach { progress ->
                    when(progress){
                        is Progress.Finished -> {
                            _state.value = _state.value.copy(
                                isUpdatingGroup = false,
                                updateMessage = "Updated"
                            )
                        }
                        is Progress.Loading -> {
                            _state.value = _state.value.copy(isUpdatingGroup = true)
                        }
                        is Progress.Error -> {
                            _state.value = _state.value.copy(
                                isUpdatingGroup = false,
                                updatingGroupError = progress.message
                            )
                        }
                        is Progress.Unauthorized -> {
                            _state.value = _state.value.copy(isUpdatingGroup = false)
                            logoutUser()
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is GroupValidationResult.GroupNameBlank -> {
                _groupName.value = _groupName.value.copy(error = "This field cannot be blank")
            }
            is GroupValidationResult.GroupDescriptionBlank -> {
                _groupDescription.value =
                    _groupDescription.value.copy(error = "This field cannot be blank")
            }
        }
    }

    fun onDeleteNameChange(value: String){
        _deleteGroupName.value = _deleteGroupName.value.copy(text = value)
    }

    fun onCreateCodeClick(){
        createInvitation(
            groupId = state.value.group.id
        ).onEach { progress ->
            when(progress){
                is Progress.Loading -> _state.value = _state.value.copy(isCreatingCode = true)
                is Progress.Finished -> _state.value = _state.value.copy(isCreatingCode = false)
                is Progress.Error -> _state.value = _state.value.copy(isCreatingCode = false)
                is Progress.Unauthorized -> {
                    _state.value = _state.value.copy(isCreatingCode = false)
                    logoutUser()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onDeleteCodeClick(){
        _state.value.invitation?.let { invitation ->
            deleteInvitation(id = invitation.id).onEach { progress ->
                when(progress){
                    is Progress.Loading -> _state.value = _state.value.copy(isDeletingCode = true)
                    is Progress.Finished -> _state.value = _state.value.copy(isDeletingCode = false)
                    is Progress.Error -> _state.value = _state.value.copy(isDeletingCode = false)
                    is Progress.Unauthorized -> {
                        _state.value = _state.value.copy(isDeletingCode = false)
                        logoutUser()
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onGroupNameChange(value: String){
        _groupName.value = _groupName.value.copy(text = value)
    }

    fun onGroupDescriptionChange(value: String){
        _groupDescription.value = _groupDescription.value.copy(text = value)
    }

}




















