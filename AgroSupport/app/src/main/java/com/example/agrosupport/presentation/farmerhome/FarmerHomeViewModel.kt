package com.example.agrosupport.presentation.farmerhome

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.agrosupport.common.GlobalVariables
import com.example.agrosupport.common.Resource
import com.example.agrosupport.common.Routes
import com.example.agrosupport.common.UIState
import com.example.agrosupport.data.local.AppDatabase
import com.example.agrosupport.data.repository.AdvisorRepository
import com.example.agrosupport.data.repository.AppointmentRepository
import com.example.agrosupport.data.repository.AuthenticationRepository
import com.example.agrosupport.data.repository.FarmerRepository
import com.example.agrosupport.data.repository.ProfileRepository
import com.example.agrosupport.domain.Advisor
import com.example.agrosupport.domain.Appointment
import com.example.agrosupport.domain.Profile
import com.example.agrosupport.domain.AuthenticationResponse
import com.example.agrosupport.presentation.farmerhistory.AdvisorAppointmentCard

class FarmerHomeViewModel(
    private val navController: NavController,
    private val profileRepository: ProfileRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val appointmentRepository: AppointmentRepository,
    private val farmerRepository: FarmerRepository,
    private val advisorRepository: AdvisorRepository
) : ViewModel() {
    private val _state = mutableStateOf(UIState<Profile>())
    val state: State<UIState<Profile>> get() = _state

    private val _expanded = mutableStateOf(false)
    val expanded: State<Boolean> get() = _expanded

    private val _appointmentCard = mutableStateOf(UIState<AdvisorAppointmentCard>())
    val appointmentCard: State<UIState<AdvisorAppointmentCard>> get() = _appointmentCard

    fun getFarmerName() {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            val result = profileRepository.searchProfile(GlobalVariables.USER_ID, GlobalVariables.TOKEN)
            if (result is Resource.Success) {
                _state.value = UIState(data = result.data)
            } else {
                _state.value = UIState(message = "Error getting profile")
            }
        }
    }

    fun getAppointment() {
        viewModelScope.launch {
            val farmerResult = farmerRepository.searchFarmerByUserId(
                GlobalVariables.USER_ID,
                GlobalVariables.TOKEN
            )
            if (farmerResult is Resource.Success) {
                val farmerId = farmerResult.data?.id
                if (farmerId == null) {
                    _appointmentCard.value = UIState(message = "Error farmer not found")
                    return@launch
                }
                val appointmentResult =
                    appointmentRepository.getAppointmentsByFarmer(farmerId, GlobalVariables.TOKEN)
                if (appointmentResult is Resource.Success) {
                    val appointments = appointmentResult.data
                    if (appointments != null) {
                        val appointment = appointments.filter { it.status == "PENDING" }
                            .minByOrNull { it.scheduledDate }
                        if (appointment != null) {
                            val advisorResult = advisorRepository.searchAdvisorByAdvisorId(appointment.advisorId, GlobalVariables.TOKEN)
                            if (advisorResult is Resource.Success) {
                                if (advisorResult.data == null) {
                                    _appointmentCard.value = UIState(message = "Error advisor not found")
                                    return@launch
                                }
                                val advisorProfileResult = profileRepository.searchProfile(advisorResult.data.userId, GlobalVariables.TOKEN)
                                if (advisorProfileResult is Resource.Success) {
                                    if (advisorProfileResult.data == null) {
                                        _appointmentCard.value =
                                            UIState(message = "Error advisor profile not found")
                                        return@launch
                                    }
                                    val appointmentCard = AdvisorAppointmentCard(
                                        id = appointment.id,
                                        advisorName = advisorProfileResult.data.firstName + " " + advisorProfileResult.data.lastName,
                                        advisorPhoto = advisorProfileResult.data.photo,
                                        message = appointment.message,
                                        status = appointment.status,
                                        scheduledDate = appointment.scheduledDate,
                                        startTime = appointment.startTime,
                                        endTime = appointment.endTime,
                                        meetingUrl = appointment.meetingUrl
                                        )
                                    _appointmentCard.value = UIState(data = appointmentCard)
                                }
                            }
                        } else {
                            _appointmentCard.value = UIState(message = "No pending appointments")
                        }
                    } else {
                        _appointmentCard.value = UIState(message = "Error getting appointments")
                    }
                } else {
                    _appointmentCard.value = UIState(message = "Error getting farmer")
                }
            }
        }
    }

    fun signOut() {
        GlobalVariables.ROLES = emptyList()
        viewModelScope.launch {
            val authResponse = AuthenticationResponse(
                id = GlobalVariables.USER_ID,
                username = "",
                token = GlobalVariables.TOKEN
            )
            authenticationRepository.deleteUser(authResponse)
            goToWelcomeSection()
        }
    }

    fun setExpanded(value: Boolean) {
        _expanded.value = value
    }

    fun goToAdvisorList() {
        navController.navigate(Routes.AdvisorList.route)
    }

    fun goToAppointmentList() {
        navController.navigate(Routes.FarmerAppointmentList.route)
    }

    fun goToWelcomeSection() {
        navController.navigate(Routes.Welcome.route)
    }

}