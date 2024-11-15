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
import com.example.agrosupport.data.repository.advisor.AdvisorRepository
import com.example.agrosupport.data.repository.appointment.AppointmentRepository
import com.example.agrosupport.data.repository.authentication.AuthenticationRepository
import com.example.agrosupport.data.repository.farmer.FarmerRepository
import com.example.agrosupport.data.repository.notification.NotificationRepository
import com.example.agrosupport.data.repository.profile.ProfileRepository
import com.example.agrosupport.domain.appointment.Appointment
import com.example.agrosupport.domain.profile.Profile
import com.example.agrosupport.domain.authentication.AuthenticationResponse
import com.example.agrosupport.presentation.farmerhistory.AppointmentCard

class FarmerHomeViewModel(
    private val navController: NavController,
    private val profileRepository: ProfileRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val appointmentRepository: AppointmentRepository,
    private val farmerRepository: FarmerRepository,
    private val advisorRepository: AdvisorRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    private val _state = mutableStateOf(UIState<Profile>())
    val state: State<UIState<Profile>> get() = _state

    private val _expanded = mutableStateOf(false)
    val expanded: State<Boolean> get() = _expanded

    private val _appointmentCard = mutableStateOf(UIState<AppointmentCard>())
    val appointmentCard: State<UIState<AppointmentCard>> get() = _appointmentCard

    private val _notificationCount = mutableStateOf(0)
    val notificationCount: State<Int> get() = _notificationCount

    fun getNotificationCount() {
        viewModelScope.launch {
            try {
                val result = notificationRepository.getNotifications(GlobalVariables.USER_ID, GlobalVariables.TOKEN)
                if (result is Resource.Success) {
                    _notificationCount.value = result.data?.size ?: 0
                }
            } catch (e: Exception) {
                _notificationCount.value = 0
            }
        }
    }

    fun getFarmerName() {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            try {
                val result = profileRepository.searchProfile(GlobalVariables.USER_ID, GlobalVariables.TOKEN)
                if (result is Resource.Success) {
                    _state.value = UIState(data = result.data)
                } else {
                    _state.value = UIState(message = "Error getting profile")
                }
            } catch (e: Exception) {
                _state.value = UIState(message = "Error getting profile: ${e.message}")
            }
        }
    }

    fun getAppointment() {
        _appointmentCard.value = UIState(isLoading = true)
        viewModelScope.launch {
            try {
                val farmerId = fetchFarmerId() ?: run {
                    _appointmentCard.value = UIState(message = "Error farmer not found")
                    return@launch
                }

                val appointment = fetchPendingAppointment(farmerId) ?: run {
                    _appointmentCard.value = UIState(message = "No pending appointments")
                    return@launch
                }

                val advisorProfile = fetchAdvisorProfile(appointment.advisorId) ?: run {
                    _appointmentCard.value = UIState(message = "Error advisor profile not found")
                    return@launch
                }

                val appointmentCard = AppointmentCard(
                    id = appointment.id,
                    advisorName = "${advisorProfile.firstName} ${advisorProfile.lastName}",
                    advisorPhoto = advisorProfile.photo,
                    message = appointment.message,
                    status = appointment.status,
                    scheduledDate = appointment.scheduledDate,
                    startTime = appointment.startTime,
                    endTime = appointment.endTime,
                    meetingUrl = appointment.meetingUrl
                )

                _appointmentCard.value = UIState(data = appointmentCard)
            } catch (e: Exception) {
                _appointmentCard.value = UIState(message = "Error getting appointment: ${e.message}")
            }
        }
    }

    private suspend fun fetchFarmerId(): Long? {
        return try {
            val farmerResult = farmerRepository.searchFarmerByUserId(
                GlobalVariables.USER_ID,
                GlobalVariables.TOKEN
            )
            (farmerResult as? Resource.Success)?.data?.id
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun fetchPendingAppointment(farmerId: Long): Appointment? {
        return try {
            val appointmentResult = appointmentRepository.getAppointmentsByFarmer(farmerId, GlobalVariables.TOKEN)
            val appointments = (appointmentResult as? Resource.Success)?.data
            appointments?.filter { it.status == "PENDING" }
                ?.minByOrNull { it.scheduledDate }
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun fetchAdvisorProfile(advisorId: Long): Profile? {
        return try {
            val advisorResult = advisorRepository.searchAdvisorByAdvisorId(advisorId, GlobalVariables.TOKEN)
            val advisor = (advisorResult as? Resource.Success)?.data ?: return null

            val advisorProfileResult = profileRepository.searchProfile(advisor.userId, GlobalVariables.TOKEN)
            (advisorProfileResult as? Resource.Success)?.data
        } catch (e: Exception) {
            null
        }
    }

    fun signOut() {
        GlobalVariables.ROLES = emptyList()
        viewModelScope.launch {
            try {
                val authResponse = AuthenticationResponse(
                    id = GlobalVariables.USER_ID,
                    username = "",
                    token = GlobalVariables.TOKEN
                )
                authenticationRepository.deleteUser(authResponse)
                goToWelcomeSection()
            } catch (e: Exception) {
                // Handle sign out error if needed
            }
        }
    }

    fun goToProfile() {
        navController.navigate(Routes.FarmerProfile.route)
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

    fun goToNotificationList() {
        navController.navigate(Routes.NotificationList.route)
    }

    fun goToExplorePosts() {
        navController.navigate(Routes.ExplorePosts.route)
    }

    fun goToAppointmentDetail(appointmentId: Long) {
        navController.navigate(Routes.FarmerAppointmentDetail.route + "/$appointmentId")
    }

    private fun goToWelcomeSection() {
        navController.navigate(Routes.Welcome.route)
    }
}