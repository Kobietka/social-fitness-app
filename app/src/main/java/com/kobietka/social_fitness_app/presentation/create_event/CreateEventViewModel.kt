package com.kobietka.social_fitness_app.presentation.create_event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobietka.social_fitness_app.domain.model.CreateEventValidationResult
import com.kobietka.social_fitness_app.domain.state.DateState
import com.kobietka.social_fitness_app.domain.state.EventTypeState
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState
import com.kobietka.social_fitness_app.domain.usecase.auth.LogoutUserUseCase
import com.kobietka.social_fitness_app.domain.usecase.event.CreateEventUseCase
import com.kobietka.social_fitness_app.domain.usecase.event.ValidateCreateEventUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.GetGroupUseCase
import com.kobietka.social_fitness_app.util.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import javax.inject.Inject
import kotlin.time.ExperimentalTime


@HiltViewModel
class CreateEventViewModel
@Inject constructor(
    handle: SavedStateHandle,
    private val getGroup: GetGroupUseCase,
    private val validateCreateEvent: ValidateCreateEventUseCase,
    private val createEvent: CreateEventUseCase,
    private val logoutUser: LogoutUserUseCase
) : ViewModel() {

    init {
        handle.get<String>("groupId")?.let { groupId ->
            getGroup(groupId = groupId).onEach { group ->
                _state.value = _state.value.copy(group = group)
            }.launchIn(viewModelScope)
        }
    }

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
    private val _eventType = mutableStateOf(EventTypeState())
    val eventType: State<EventTypeState> = _eventType

    private val _eventTypes = mutableStateOf(
        listOf(
            EventTypeState(name = "Repetition", code = "REP"),
            EventTypeState(name = "Time", code = "TIME"),
            EventTypeState(name = "Less time", code = "LESS_TIME")
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
        _eventName.value = _eventName.value.copy(error = "")
        _eventDescription.value = _eventDescription.value.copy(error = "")
        _pointGoal.value = _pointGoal.value.copy(error = "")
        _pointPerMin.value = _pointPerMin.value.copy(error = "")
        _pointPerRep.value = _pointPerRep.value.copy(error = "")
        _startDate.value = _startDate.value.copy(error = "")
        _endDate.value = _endDate.value.copy(error = "")
        _eventType.value = _eventType.value.copy(error = "")

        val name = _eventName.value.text.trim()
        val description = _eventDescription.value.text.trim()
        val pointGoal = _pointGoal.value.text.trim()
        val pointsPerMinute = _pointPerMin.value.text.trim()
        val pointsPerRepetition = _pointPerRep.value.text.trim()
        val startDate = _startDate.value.formatted
        val endDate = _endDate.value.formatted
        val eventType = _eventType.value

        val validationResults = validateCreateEvent(
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
            createEvent(
                groupId = state.value.group!!.id,
                name = name,
                description = description,
                pointGoal = pointGoal.toInt(),
                pointPerMinute = pointsPerMinute.toInt(),
                pointPerRep = pointsPerRepetition.toInt(),
                startDate = startDate!!,
                endDate = endDate!!,
                eventType = eventType.code!!
            ).onEach { progress ->
                when(progress){
                    is Progress.Finished -> {
                        _state.value = _state.value.copy(isLoading = false)
                        onSuccess()
                    }
                    is Progress.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Progress.Unauthorized -> {
                        _state.value = _state.value.copy(isLoading = false)
                        logoutUser()
                    }
                    is Progress.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            createEventError = progress.message
                        )
                    }
                }
            }.launchIn(viewModelScope)
        } else {
            validationResults.forEach { result ->
                when(result){
                    is CreateEventValidationResult.NameBlank -> {
                        _eventName.value = _eventName.value.copy(error = "This field cannot be blank")
                    }
                    is CreateEventValidationResult.DescriptionBlank -> {
                        _eventDescription.value = _eventDescription.value.copy(error = "This field cannot be blank")
                    }
                    is CreateEventValidationResult.PointGoalBlank -> {
                        _pointGoal.value = _pointGoal.value.copy(error = "This field cannot be blank")
                    }
                    is CreateEventValidationResult.PointsPerMinuteBlank -> {
                        _pointPerMin.value = _pointPerMin.value.copy(error = "This field cannot be blank")
                    }
                    is CreateEventValidationResult.PointsPerRepetitionBlank -> {
                        _pointPerRep.value = _pointPerRep.value.copy(error = "This field cannot be blank")
                    }
                    is CreateEventValidationResult.PointGoalNegative -> {
                        _pointGoal.value = _pointGoal.value.copy(error = "This number cannot be negative")
                    }
                    is CreateEventValidationResult.PointsPerMinuteNegative -> {
                        _pointPerMin.value = _pointPerMin.value.copy(error = "This number cannot be negative")
                    }
                    is CreateEventValidationResult.PointsPerRepetitionNegative -> {
                        _pointPerRep.value = _pointPerRep.value.copy(error = "This number cannot be negative")
                    }
                    is CreateEventValidationResult.StartDateNotSelected -> {
                        _startDate.value = _startDate.value.copy(error = "Please select start date")
                    }
                    is CreateEventValidationResult.EndDateNotSelected -> {
                        _endDate.value = _endDate.value.copy(error = "Please select end date")
                    }
                    is CreateEventValidationResult.EventTypeNotSelected -> {
                        _eventType.value = _eventType.value.copy(error = "Please select event type")
                    }
                }
            }
        }

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





















