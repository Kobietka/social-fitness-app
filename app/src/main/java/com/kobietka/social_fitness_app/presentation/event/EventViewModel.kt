package com.kobietka.social_fitness_app.presentation.event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.model.EventType
import com.kobietka.social_fitness_app.domain.usecase.auth.LogoutUserUseCase
import com.kobietka.social_fitness_app.domain.usecase.event.GetEventMembersUseCase
import com.kobietka.social_fitness_app.domain.usecase.event.GetEventUseCase
import com.kobietka.social_fitness_app.domain.usecase.event.GetRemoteEventUseCase
import com.kobietka.social_fitness_app.domain.usecase.event.MatchEventMembersWithActivitiesUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.GetGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.main.GetUsersUseCase
import com.kobietka.social_fitness_app.util.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class EventViewModel
@Inject constructor(
    handle: SavedStateHandle,
    private val getEvent: GetEventUseCase,
    private val getUsers: GetUsersUseCase,
    private val getRemoteEvent: GetRemoteEventUseCase,
    private val logoutUser: LogoutUserUseCase,
    private val getGroup: GetGroupUseCase,
    private val getEventMembers: GetEventMembersUseCase,
    private val matchEventMembersWithActivities: MatchEventMembersWithActivitiesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(EventScreenState())
    val state: State<EventScreenState> = _state

    init {
        handle.get<String>("eventId")?.let { eventId ->
            handle.get<String>("groupId")?.let { groupId ->
                getRemoteEvent(
                    eventId = eventId,
                    groupId = groupId
                ).onEach { progress ->
                    when(progress){
                        is Progress.Finished -> {
                            _state.value = _state.value.copy(loggedUser = getUsers().first().first())
                            getEvent(eventId = eventId).onEach { event ->
                                _state.value = _state.value.copy(event = event)
                                getEventMembers(eventId = eventId).onEach { eventMembers ->
                                    when(event.eventType){
                                        EventType.LESS_TIME -> {
                                            _state.value = _state.value.copy(
                                                eventMembers = matchEventMembersWithActivities(
                                                    eventId = eventId,
                                                    eventMembers = eventMembers
                                                ).sortedBy {
                                                    val fastest = it.activities.minByOrNull { activity -> activity.value }
                                                    fastest?.value ?: it.totalScore
                                                }
                                            )
                                        }
                                        else -> {
                                            _state.value = _state.value.copy(
                                                eventMembers = matchEventMembersWithActivities(
                                                    eventId = eventId,
                                                    eventMembers = eventMembers
                                                ).sortedBy { it.totalScore }.asReversed()
                                            )
                                        }
                                    }
                                }.launchIn(viewModelScope)
                            }.launchIn(viewModelScope)
                            _state.value.loggedUser?.let { loggedUser ->
                                val group = getGroup(groupId = groupId).first()
                                group?.let {
                                    _state.value = _state.value.copy(group = group)
                                    if(group.ownerId == loggedUser.id)
                                        _state.value = _state.value.copy(isUserAGroupOwner = true)
                                }
                            }
                        }
                        is Progress.Unauthorized -> logoutUser()
                        else -> { }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

}

















