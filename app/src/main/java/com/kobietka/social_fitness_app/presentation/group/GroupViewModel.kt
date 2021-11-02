package com.kobietka.social_fitness_app.presentation.group


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.usecase.auth.LogoutUserUseCase
import com.kobietka.social_fitness_app.domain.usecase.event.GetEventsForGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.GetGroupMembersUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.GetGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.GetRemoteGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.main.GetUsersUseCase
import com.kobietka.social_fitness_app.domain.usecase.post.GetPostsForGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.post.MatchPostsWithMembersUseCase
import com.kobietka.social_fitness_app.util.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.Exception


@HiltViewModel
class GroupViewModel
@Inject constructor(
    handle: SavedStateHandle,
    getUsers: GetUsersUseCase,
    private val getGroup: GetGroupUseCase,
    private val getPostsForGroup: GetPostsForGroupUseCase,
    private val matchPostsWithMembers: MatchPostsWithMembersUseCase,
    private val getRemoteGroup: GetRemoteGroupUseCase,
    private val getEventsForGroup: GetEventsForGroupUseCase,
    private val getGroupMembers: GetGroupMembersUseCase,
    private val logoutUser: LogoutUserUseCase
) : ViewModel() {

    private val _state = mutableStateOf(GroupScreenState())
    val state: State<GroupScreenState> = _state

    init {
        getUsers().onEach { users->
            try {
                _state.value = _state.value.copy(user = users.first())
            } catch (exception: Exception){ }
        }.launchIn(viewModelScope)
        handle.get<String>("groupId")?.let { groupId ->
            getGroup(groupId = groupId).onEach { group ->
                _state.value = _state.value.copy(group = group)
            }.launchIn(viewModelScope)
            getRemoteGroup(groupId = groupId).onEach { progress ->
                when(progress){
                    is Progress.Loading -> _state.value = _state.value.copy(isUpdating = true)
                    is Progress.Finished -> {
                        delay(1000)
                        _state.value = _state.value.copy(isUpdating = false)
                    }
                    is Progress.Error -> {
                        _state.value = _state.value.copy(isUpdating = false, updateError = progress.message)
                        delay(2000)
                        _state.value = _state.value.copy(updateError = "")
                    }
                    is Progress.Unauthorized -> {
                        _state.value = _state.value.copy(isUpdating = false)
                        logoutUser()
                    }
                }
            }.launchIn(viewModelScope)
            getEventsForGroup(groupId = groupId).onEach { events ->
                _state.value = _state.value.copy(events = events)
            }.launchIn(viewModelScope)
            getGroupMembers(groupId = groupId).onEach { members ->
                _state.value = _state.value.copy(members = members)
            }.launchIn(viewModelScope)
            getPostsForGroup(groupId = groupId).onEach { postEntities ->
                _state.value = _state.value.copy(
                    posts = matchPostsWithMembers(groupId = groupId, postEntities = postEntities)
                )
            }.launchIn(viewModelScope)
        }
    }

}





















