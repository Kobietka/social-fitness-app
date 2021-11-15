package com.kobietka.social_fitness_app.presentation.create_event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kobietka.social_fitness_app.domain.state.DateState
import com.kobietka.social_fitness_app.domain.state.EventTypeState
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.util.*
import javax.inject.Inject
import kotlin.time.ExperimentalTime


@HiltViewModel
class CreateEventViewModel
@Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(CreateEventScreenState())
    val state: State<CreateEventScreenState> = _state

    private val _eventName = mutableStateOf(StandardTextFieldState(label = "Event name"))
    val eventName: State<StandardTextFieldState> = _eventName

    private val _eventDescription = mutableStateOf(StandardTextFieldState(label = "Event description"))
    val eventDescription: State<StandardTextFieldState> = _eventDescription

    private val _pointGoal = mutableStateOf(StandardTextFieldState(label = "Point goal"))
    val pointGoal: State<StandardTextFieldState> = _pointGoal

    private val _pointPerRep = mutableStateOf(StandardTextFieldState(label = "Points per repetition"))
    val pointPerRep: State<StandardTextFieldState> = _pointPerRep

    private val _pointPerMin = mutableStateOf(StandardTextFieldState(label = "Points per minute"))
    val pointPerMin: State<StandardTextFieldState> = _pointPerMin

    private val _dropdownMenuExpanded = mutableStateOf(false)
    val dropdownMenuExpanded: State<Boolean> = _dropdownMenuExpanded

    // YYYY-MM-DD
    private val _startDate = mutableStateOf(DateState())
    val startDate: State<DateState> = _startDate

    // YYYY-MM-DD
    private val _endDate = mutableStateOf(DateState())
    val endDate: State<DateState> = _endDate

    // TIME REP LESS_TIME
    private val _eventType = mutableStateOf<EventTypeState?>(null)
    val eventType: State<EventTypeState?> = _eventType

    private val _eventTypes = mutableStateOf(
        listOf(
            EventTypeState(name = "Repetition", code = "REP"),
            EventTypeState(name = "Time", code = "TIME"),
            EventTypeState("Less time", code = "LESS_TIME")
        )
    )
    val eventTypes: State<List<EventTypeState>> = _eventTypes

    fun onEventNameChange(value: String){
        _eventName.value = _eventName.value.copy(text = value)
    }

    fun onEventDescriptionChange(value: String){
        _eventDescription.value = _eventDescription.value.copy(text = value)
    }

    fun onPointGoalChange(value: String){
        _pointGoal.value = _pointGoal.value.copy(text = value)
    }

    fun onPointPerRepChange(value: String){
        _pointPerRep.value = _pointPerRep.value.copy(text = value)
    }

    fun onPointPerMinChange(value: String){
        _pointPerMin.value = _pointPerMin.value.copy(text = value)
    }

    fun onEventTypeSelected(code: String){
        _eventType.value = _eventTypes.value.first { it.code == code }
        _dropdownMenuExpanded.value = false
    }

    fun onEventTypeClick(){
        _dropdownMenuExpanded.value = true
    }

    fun onMenuDismiss(){
        _dropdownMenuExpanded.value = false
    }

    fun onCreateEventClick(onSuccess: () -> Unit){
        onSuccess()
    }

    @ExperimentalTime
    fun onStartDateChange(value: Long){
        val endDateString = _endDate.value.instantString
        val startInstant = Instant.fromEpochMilliseconds(value)
        val startDate = startInstant.toLocalDateTime(TimeZone.currentSystemDefault())

        if(endDateString != null){
            val endInstant = Instant.parse(endDateString)

            val diff = endInstant.minus(startInstant)
            if(diff.isPositive()){
                _startDate.value = _startDate.value.copy(
                    instantString = startInstant.toString(),
                    formatted = "${startDate.year}-${startDate.monthNumber}-${startDate.dayOfMonth}"
                )
            }
        } else {
            _startDate.value = _startDate.value.copy(
                instantString = startInstant.toString(),
                formatted = "${startDate.year}-${startDate.monthNumber}-${startDate.dayOfMonth}"
            )
        }
    }

    @ExperimentalTime
    fun onEndDateChange(value: Long){
        val startDateString = _startDate.value.instantString
        val endInstant = Instant.fromEpochMilliseconds(value)
        val endDate = endInstant.toLocalDateTime(TimeZone.currentSystemDefault())

        if(startDateString != null){
            val startInstant = Instant.parse(startDateString)

            val diff = startInstant.minus(endInstant)
            if(diff.isNegative()){
                _endDate.value = _endDate.value.copy(
                    instantString = endInstant.toString(),
                    formatted = "${endDate.year}-${endDate.monthNumber}-${endDate.dayOfMonth}"
                )
            }
        } else {
            _endDate.value = _endDate.value.copy(
                instantString = endInstant.toString(),
                formatted = "${endDate.year}-${endDate.monthNumber}-${endDate.dayOfMonth}"
            )
        }
    }

}





















