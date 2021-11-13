package com.kobietka.social_fitness_app.presentation.create_event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CreateEventViewModel
@Inject constructor() : ViewModel() {

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

    // YYYY-MM-DD
    private val _startDate = mutableStateOf<String?>(null)
    val startDate: State<String?> = _startDate

    // YYYY-MM-DD
    private val _endDate = mutableStateOf<String?>(null)
    val endDate: State<String?> = _endDate

    // TIME REP LESS_TIME
    private val _eventType = mutableStateOf<String?>(null)
    val eventType: State<String?> = _eventType

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

    fun onStartDateChange(value: Long){
        _startDate.value = value.toString()
    }

}





















