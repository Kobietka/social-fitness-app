package com.kobietka.social_fitness_app.presentation.create_activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.model.ActivityValidationResult
import com.kobietka.social_fitness_app.domain.model.EventType
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import com.kobietka.social_fitness_app.domain.usecase.activity.CreateActivityUseCase
import com.kobietka.social_fitness_app.domain.usecase.activity.ValidateActivityUseCase
import com.kobietka.social_fitness_app.domain.usecase.auth.LogoutUserUseCase
import com.kobietka.social_fitness_app.domain.usecase.event.GetEventUseCase
import com.kobietka.social_fitness_app.util.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class CreateActivityViewModel
@Inject constructor(
    handle: SavedStateHandle,
    private val getEvent: GetEventUseCase,
    private val validateActivity: ValidateActivityUseCase,
    private val logoutUser: LogoutUserUseCase,
    private val createActivity: CreateActivityUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CreateActivityScreenState())
    val state: State<CreateActivityScreenState> = _state

    private val _activityName = mutableStateOf(StandardTextFieldState(label = "Activity name"))
    val activityName: State<StandardTextFieldState> = _activityName

    private val _repetitions = mutableStateOf(StandardTextFieldState(text = "0", label = "Repetitions"))
    val repetitions: State<StandardTextFieldState> = _repetitions

    private val _minutes = mutableStateOf(StandardTextFieldState(text = "0", label = "Minutes"))
    val minutes: State<StandardTextFieldState> = _minutes

    private val _seconds = mutableStateOf(StandardTextFieldState(text = "0", label = "Seconds"))
    val seconds: State<StandardTextFieldState> = _seconds

    init {
        handle.get<String>("eventId")?.let { eventId ->
            getEvent(eventId = eventId).onEach { event ->
                _state.value = _state.value.copy(event = event)
            }.launchIn(viewModelScope)
        }
    }

    fun onCreateActivityClick(onSuccess: () -> Unit){
        _activityName.value = _activityName.value.copy(error = "")
        _repetitions.value = _repetitions.value.copy(error = "")
        _minutes.value = _minutes.value.copy(error = "")
        _seconds.value = _seconds.value.copy(error = "")
        _state.value = _state.value.copy(error = null)

        state.value.event?.let { event ->
            val name = _activityName.value.text.trim()
            val reps = when(event.eventType){
                EventType.REPETITION -> _repetitions.value.text.trim()
                else -> "0"
            }
            val min = when(event.eventType){
                EventType.TIME -> _minutes.value.text.trim()
                EventType.LESS_TIME -> _minutes.value.text.trim()
                else -> "0"
            }
            val secs = when(event.eventType){
                EventType.TIME -> _seconds.value.text.trim()
                EventType.LESS_TIME -> _seconds.value.text.trim()
                else -> "0"
            }

            val validationResults = validateActivity(
                activityName = name,
                repetitions = reps,
                minutes = min,
                seconds = secs
            )

            if(validationResults.isEmpty()){
                val value = when(event.eventType){
                    EventType.REPETITION -> reps.toInt()
                    EventType.TIME -> min.toInt() * 60 + secs.toInt()
                    EventType.LESS_TIME -> min.toInt() * 60 + secs.toInt()
                    EventType.UNKNOWN -> 0
                }
                createActivity(
                    eventId = event.id,
                    name = name,
                    value = value
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
                        is ActivityValidationResult.NameBlank -> {
                            _activityName.value = _activityName.value.copy(error = "This field cannot be empty")
                        }
                        is ActivityValidationResult.RepetitionsBlank -> {
                            _repetitions.value = _repetitions.value.copy(error = "This field cannot be empty")
                        }
                        is ActivityValidationResult.MinutesBlank -> {
                            _minutes.value = _minutes.value.copy(error = "This field cannot be empty")
                        }
                        is ActivityValidationResult.SecondsBlank -> {
                            _seconds.value = _seconds.value.copy(error = "This field cannot be empty")
                        }
                        is ActivityValidationResult.RepetitionsNegative -> {
                            _repetitions.value = _repetitions.value.copy(error = "This value cannot be negative")
                        }
                        is ActivityValidationResult.MinutesNegative -> {
                            _minutes.value = _minutes.value.copy(error = "This value cannot be negative")
                        }
                        is ActivityValidationResult.SecondsNegative -> {
                            _seconds.value = _seconds.value.copy(error = "This value cannot be negative")
                        }
                        is ActivityValidationResult.RepetitionsNotANumber -> {
                            _repetitions.value = _repetitions.value.copy(error = "Please enter a number")
                        }
                        is ActivityValidationResult.MinutesNotANumber -> {
                            _minutes.value = _minutes.value.copy(error = "Please enter a number")
                        }
                        is ActivityValidationResult.SecondsNotANumber -> {
                            _seconds.value = _seconds.value.copy(error = "Please enter a number")
                        }
                    }
                }
            }
        }
    }

    fun onActivityNameChange(value: String){
        _activityName.value = _activityName.value.copy(text = value)
    }

    fun onRepetitionsChange(value: String){
        _repetitions.value = _repetitions.value.copy(text = value)
    }

    fun onMinutesChange(value: String){
        _minutes.value = _minutes.value.copy(text = value)
    }

    fun onSecondsChange(value: String){
        _seconds.value = _seconds.value.copy(text = value)
    }

}























