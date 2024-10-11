package com.example.agrosupport.presentation.farmerappointments

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrosupport.common.GlobalVariables
import com.example.agrosupport.common.Resource
import com.example.agrosupport.common.Routes
import com.example.agrosupport.common.UIState
import com.example.agrosupport.data.repository.AdvisorRepository
import com.example.agrosupport.data.repository.AppointmentRepository
import com.example.agrosupport.data.repository.FarmerRepository
import com.example.agrosupport.data.repository.ProfileRepository
import com.example.agrosupport.presentation.farmerhistory.AdvisorAppointmentCard
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FarmerAppointmentListViewModel(
    private val navController: NavController,
    private val profileRepository: ProfileRepository,
    private val advisorRepository: AdvisorRepository,
    private val appointmentRepository: AppointmentRepository,
    private val farmerRepository: FarmerRepository
) : ViewModel() {

    private val _state = mutableStateOf(UIState<List<AdvisorAppointmentCard>>())
    val state: State<UIState<List<AdvisorAppointmentCard>>> get() = _state

    fun goBack() {
        navController.popBackStack()
    }

    fun goAppointmentDetail(appointmentId: Long) {
        navController.navigate(Routes.FarmerAppointmentDetail.route + "/$appointmentId")
    }

    fun goHistory() {
        navController.navigate(Routes.FarmerAppointmentHistory.route)
    }

    fun getAdvisorAppointmentListByFarmer(selectedDate: Date? = null) {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            val farmerResult = farmerRepository.searchFarmerByUserId(GlobalVariables.EXAMPLE_USER_ID, GlobalVariables.EXAMPLE_TOKEN)

            if (farmerResult is Resource.Success && farmerResult.data != null) {
                val farmerId = farmerResult.data.id // Si la búsqueda del granjero fue exitosa
                val result = appointmentRepository.getAppointmentsByFarmer(farmerId, GlobalVariables.EXAMPLE_TOKEN)
                if (result is Resource.Success) {
                    var appointments = result.data?.filter { it.status == "PENDING" || it.status == "ONGOING" }

                    // Aplicar filtro de fecha si se ha proporcionado una fecha seleccionada
                    if (selectedDate != null) {
                        val formattedSelectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
                        appointments = appointments?.filter {
                            it.scheduledDate.startsWith(formattedSelectedDate)
                        }
                    }

                    // Ordenar las citas por fecha (scheduledDate) y luego por hora de inicio (startTime)
                    appointments = appointments?.sortedWith(
                        compareBy(
                            { SimpleDateFormat("yyyy-MM-dd").parse(it.scheduledDate) },
                            { SimpleDateFormat("HH:mm").parse(it.startTime) }
                        )
                    )



                    if (appointments != null && appointments.isNotEmpty()) {
                        val advisorAppointmentCards = mutableListOf<AdvisorAppointmentCard>()
                        for (appointment in appointments) {
                            val advisorResult = advisorRepository.searchAdvisorByAdvisorId(appointment.advisorId, GlobalVariables.EXAMPLE_TOKEN)
                            val advisorName = if (advisorResult is Resource.Success) {
                                val advisor = advisorResult.data
                                val profileResult = advisor?.userId?.let { userId ->
                                    profileRepository.searchProfile(userId, GlobalVariables.EXAMPLE_TOKEN)
                                }
                                if (profileResult is Resource.Success) {
                                    val profile = profileResult.data
                                    "${profile?.firstName ?: "Asesor"} ${profile?.lastName ?: "Desconocido"}"
                                } else {
                                    "Asesor Desconocido"
                                }
                            } else {
                                "Asesor Desconocido"
                            }

                            val advisorPhoto = if (advisorResult is Resource.Success) {
                                val advisor = advisorResult.data
                                val profileResult = advisor?.userId?.let { userId ->
                                    profileRepository.searchProfile(userId, GlobalVariables.EXAMPLE_TOKEN)
                                }
                                if (profileResult is Resource.Success) {
                                    val profile = profileResult.data
                                    profile?.photo ?: "Asesor Desconocido"
                                } else {
                                    "Asesor Desconocido"
                                }
                            } else {
                                "Asesor Desconocido"
                            }
                            advisorAppointmentCards.add(
                                AdvisorAppointmentCard(
                                    id = appointment.id,
                                    advisorName = advisorName,
                                    advisorPhoto = advisorPhoto,
                                    message = appointment.message,
                                    status = appointment.status,
                                    scheduledDate = appointment.scheduledDate,
                                    startTime = appointment.startTime,
                                    endTime = appointment.endTime,
                                    meetingUrl = appointment.meetingUrl
                                )
                            )
                        }
                        _state.value = UIState(data = advisorAppointmentCards)
                    } else {
                        _state.value = UIState(message = "No se encontraron citas para la fecha seleccionada")
                    }
                } else if (result is Resource.Error) {
                    _state.value = UIState(message = "Error al intentar obtener las citas")
                }
            } else {
                _state.value = UIState(message = "Error al intentar obtener información del usuario")
            }
        }
    }
}
