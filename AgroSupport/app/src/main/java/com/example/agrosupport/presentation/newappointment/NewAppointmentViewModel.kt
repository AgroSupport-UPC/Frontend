package com.example.agrosupport.presentation.newappointment

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrosupport.common.GlobalVariables
import com.example.agrosupport.common.Resource
import com.example.agrosupport.common.UIState
import com.example.agrosupport.data.repository.AppointmentRepository
import com.example.agrosupport.data.repository.AvailableDateRepository
import com.example.agrosupport.domain.Appointment
import com.example.agrosupport.domain.AvailableDate
import kotlinx.coroutines.launch


class NewAppointmentViewModel(private val navController: NavController,
                              private val availableDateRepository: AvailableDateRepository,
                              private val appointmentRepository: AppointmentRepository
): ViewModel() {
    private val _state = mutableStateOf(UIState<List<AvailableDate>>())
    val state: State<UIState<List<AvailableDate>>> get() = _state

    private val _comment = mutableStateOf("")
    val comment: State<String> get() = _comment

    private val _isExpanded = mutableStateOf(false)
    val isExpanded: State<Boolean> get() = _isExpanded

    private val _selectedDate = mutableIntStateOf(-1)
    val selectedDate: State<Int> get() = _selectedDate

    fun goBack() {
        navController.popBackStack()
    }

    fun getAvailableDates(advisorId: Long) {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            val result = availableDateRepository.getAvailableDatesByAdvisor(advisorId, GlobalVariables.TOKEN)
            if (result is Resource.Success) {
                val availableDates = result.data ?: run {
                    _state.value = UIState(message = "No se encontraron fechas disponibles para este asesor")
                    return@launch
                }
                _state.value = UIState(data = availableDates)
            } else {
                _state.value = UIState(message = "Error al obtener las fechas disponibles")
            }
        }
    }

    fun createAppointment(advisorId: Long, farmerId: Long) {
        state.value.data?.get(selectedDate.value)?.let { availableDate ->
            val appointment = Appointment(
                id = 0,
                advisorId = advisorId,
                farmerId = farmerId,
                message = comment.value,
                status = "PENDING",
                scheduledDate = availableDate.availableDate,
                startTime = availableDate.startTime,
                endTime = availableDate.endTime,
                meetingUrl = ""
            )
            _state.value = UIState(isLoading = true)
            viewModelScope.launch {
                val result = appointmentRepository.createAppointment(GlobalVariables.TOKEN, appointment)
                if (result is Resource.Success) {
                    _state.value = UIState(data = emptyList())
                } else {
                    _state.value = UIState(message = "Error al crear la cita")
                }
            }
            navController.popBackStack()
        }



    }

    fun setComment(comment: String) {
        _comment.value = comment
    }

    fun toggleExpanded() {
        _isExpanded.value = !_isExpanded.value
    }

    fun setSelectedDate(dateId: Int) {
        _selectedDate.intValue = dateId
    }
}