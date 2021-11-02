package com.kobietka.social_fitness_app.presentation.group


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.usecase.event.GetEventsForGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.GetGroupMembersUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.GetGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.GetRemoteGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.main.GetUsersUseCase
import com.kobietka.social_fitness_app.domain.usecase.post.GetPostsForGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.post.MatchPostsWithMembersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val getGroupMembers: GetGroupMembersUseCase
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
                _state.value = _state.value.copy(
                    group = group,
                    isOwner = _state.value.user.id == group.ownerId
                )
            }.launchIn(viewModelScope)
            getRemoteGroup(groupId = groupId).onEach {
                /* add update animation */
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





















