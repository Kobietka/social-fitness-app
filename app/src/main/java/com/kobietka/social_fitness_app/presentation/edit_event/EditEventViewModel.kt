package com.kobietka.social_fitness_app.presentation.edit_event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.model.EventType
import com.kobietka.social_fitness_app.domain.model.EventValidationResult
import com.kobietka.social_fitness_app.domain.state.EventTypeState
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import com.kobietka.social_fitness_app.domain.usecase.auth.LogoutUserUseCase
import com.kobietka.social_fitness_app.domain.usecase.event.EditEventUseCase
import com.kobietka.social_fitness_app.domain.usecase.event.GetEventUseCase
import com.kobietka.social_fitness_app.domain.usecase.event.ValidateEventUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.GetGroupUseCase
import com.kobietka.social_fitness_app.util.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class EditEventViewModel
@Inject constructor(
    handle: SavedStateHandle,
    private val getEvent: GetEventUseCase,
    private val getGroup: GetGroupUseCase,
    private val editEvent: EditEventUseCase,
    private val validateEvent: ValidateEventUseCase,
    private val logoutUser: LogoutUserUseCase
) : ViewModel() {

    private val _state = mutableStateOf(EditEventScreenState())
    val state: State<EditEventScreenState> = _state

    private val _eventName = mutableStateOf(StandardTextFieldState(label = "Event name"))
    val eventName: State<StandardTextFieldState> = _eventName

    private val _eventDescription = mutableStateOf(StandardTextFieldState(label = "Event description"))
    val eventDescription: State<StandardTextFieldState> = _eventDescription

    private val _eventType = mutableStateOf<EventTypeState?>(null)
    val eventType: State<EventTypeState?> = _eventType

    private val _pointGoal = mutableStateOf(StandardTextFieldState(label = "Point goal"))
    val pointGoal: State<StandardTextFieldState> = _pointGoal

    private val _pointsPerMinute = mutableStateOf(StandardTextFieldState(label = "Points per minute"))
    val pointsPerMinute: State<StandardTextFieldState> = _pointsPerMinute

    private val _pointsPerRepetition = mutableStateOf(StandardTextFieldState(label = "Points per repetition"))
    val pointsPerRepetition: State<StandardTextFieldState> = _pointsPerRepetition

    private val _startDate = mutableStateOf<String?>(null)
    val startDate: State<String?> = _startDate

    private val _endDate = mutableStateOf<String?>(null)
    val endDate: State<String?> = _endDate

    init {
        handle.get<String>("eventId")?.let { eventId ->
            getEvent(eventId = eventId).onEach { event ->
                _state.value = _state.value.copy(event = event)
                _eventName.value = _eventName.value.copy(text = event.name)
                _eventDescription.value = _eventDescription.value.copy(text = event.description)
                _pointGoal.value = _pointGoal.value.copy(text = event.pointGoal.toString())
                _pointsPerMinute.value = _pointsPerMinute.value.copy(text = event.pointPerMinute.toString())
                _pointsPerRepetition.value = _pointsPerRepetition.value.copy(text = event.pointPerRep.toString())
                _eventType.value = when(event.eventType){
                    EventType.TIME -> EventTypeState(name = "Time", code = "TIME")
                    EventType.REPETITION -> EventTypeState(name = "Repetition", code = "REP")
                    EventType.LESS_TIME -> EventTypeState(name = "Less time", code = "LESS_TIME")
                    else -> EventTypeState(name = "Unknown", code = "UNKNOWN")
                }
                _startDate.value = event.startDate
                _endDate.value = event.endDate
            }.launchIn(viewModelScope)
        }
        handle.get<String>("groupId")?.let { groupId ->
            getGroup(groupId = groupId).onEach { group ->
                _state.value = _state.value.copy(group = group)
            }.launchIn(viewModelScope)
        }
    }

    fun onEditEventClick(onSuccess: () -> Unit){
        _eventName.value = _eventName.value.copy(error = "")
        _eventDescription.value = _eventDescription.value.copy(error = "")
        _pointGoal.value = _pointGoal.value.copy(error = "")
        _pointsPerMinute.value = _pointsPerMinute.value.copy(error = "")
        _pointsPerRepetition.value = _pointsPerRepetition.value.copy(error = "")
        _eventType.value?.let { typeState ->
            _eventType.value = typeState.copy(error = "")
        }

        val name = _eventName.value.text.trim()
        val description = _eventDescription.value.text.trim()
        val eventType = _eventType.value
        eventType?.let {
            val pointGoal = when (eventType.code) {
                "TIME" -> _pointGoal.value.text.trim()
                "REP" -> _pointGoal.value.text.trim()
                else -> "0"
            }
            val pointsPerMinute = when (eventType.code) {
                "TIME" -> _pointsPerMinute.value.text.trim()
                else -> "0"
            }
            val pointsPerRepetition = when (eventType.code) {
                "REP" -> _pointsPerRepetition.value.text.trim()
                else -> "0"
            }
            val startDate = _startDate.value
            val endDate = _endDate.value

            val validationResults = validateEvent(
                name = name,
                description = description,
                pointGoal = pointGoal,
                pointsPerMinute = pointsPerMinute,
                pointsPerRepetition = pointsPerRepetition,
                startDate = startDate,
                endDate = endDate,
                eventType = eventType
            )

            if(validationResults.isEmpty()){
                editEvent(
                    groupId = state.value.group!!.id,
                    eventId = state.value.event!!.id,
                    name = name,
                    description = description,
                    pointGoal = pointGoal.toInt(),
                    pointPerMinute = pointsPerMinute.toInt(),
                    pointPerRep = pointsPerRepetition.toInt()
                ).onEach { progress ->
                    when(progress){
                        is Progress.Loading -> _state.value = _state.value.copy(isLoading = true)
                        is Progress.Finished -> {
                            _state.value = _state.value.copy(isLoading = false)
                            onSuccess()
                        }
                        is Progress.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                error = progress.message
                            )
                        }
                        is Progress.Unauthorized -> {
                            _state.value = _state.value.copy(isLoading = false)
                            logoutUser()
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                validationResults.forEach { result ->
                    when(result){
                        is EventValidationResult.NameBlank -> {
                            _eventName.value = _eventName.value.copy(error = "This field cannot be blank")
                        }
                        is EventValidationResult.DescriptionBlank -> {
                            _eventDescription.value = _eventDescription.value.copy(error = "This field cannot be blank")
                        }
                        is EventValidationResult.PointGoalBlank -> {
                            _pointGoal.value = _pointGoal.value.copy(error = "This field cannot be blank")
                        }
                        is EventValidationResult.PointsPerMinuteBlank -> {
                            _pointsPerMinute.value = _pointsPerMinute.value.copy(error = "This field cannot be blank")
                        }
                        is EventValidationResult.PointsPerRepetitionBlank -> {
                            _pointsPerRepetition.value = _pointsPerRepetition.value.copy(error = "This field cannot be blank")
                        }
                        is EventValidationResult.PointGoalNegative -> {
                            _pointGoal.value = _pointGoal.value.copy(error = "This number cannot be negative")
                        }
                        is EventValidationResult.PointsPerMinuteNegative -> {
                            _pointsPerMinute.value = _pointsPerMinute.value.copy(error = "This number cannot be negative")
                        }
                        is EventValidationResult.PointsPerRepetitionNegative -> {
                            _pointsPerRepetition.value = _pointsPerRepetition.value.copy(error = "This number cannot be negative")
                        }
                        is EventValidationResult.PointGoalNotANumber -> {
                            _pointGoal.value = _pointGoal.value.copy(error = "Please enter a number")
                        }
                        is EventValidationResult.PointsPerMinuteNotANumber -> {
                            _pointsPerMinute.value = _pointsPerMinute.value.copy(error = "Please enter a number")
                        }
                        is EventValidationResult.PointsPerRepetitionNotANumber -> {
                            _pointsPerRepetition.value = _pointsPerRepetition.value.copy(error = "Please enter a number")
                        }
                        else -> { }
                    }
                }
            }
        }
    }

    fun onEventNameChange(value: String){
        _eventName.value = _eventName.value.copy(text = value)
    }

    fun onEventDescriptionChange(value: String){
        _eventDescription.value = _eventDescription.value.copy(text = value)
    }

    fun onPointGoalChange(value: String){
        _pointGoal.value = _pointGoal.value.copy(text = value)
    }

    fun onPointsPerMinuteChange(value: String){
        _pointsPerMinute.value = _pointsPerMinute.value.copy(text = value)
    }

    fun onPointsPerRepetitionChange(value: String){
        _pointsPerRepetition.value = _pointsPerRepetition.value.copy(text = value)
    }

}






















