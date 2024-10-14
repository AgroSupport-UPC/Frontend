package com.example.agrosupport.presentation.farmerappointmentdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrosupport.data.repository.AdvisorRepository
import com.example.agrosupport.data.repository.AppointmentRepository
import com.example.agrosupport.data.repository.ProfileRepository
import com.example.agrosupport.presentation.farmerhistory.AdvisorAppointmentCard
import com.example.agrosupport.common.Resource
import kotlinx.coroutines.launch
import com.example.agrosupport.common.GlobalVariables
import com.example.agrosupport.common.Routes

class FarmerAppointmentDetailViewModel(
    private val navController: NavController,
    private val appointmentRepository: AppointmentRepository,
    private val advisorRepository: AdvisorRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val _appointmentDetails = MutableLiveData<AdvisorAppointmentCard?>()
    val appointmentDetails: LiveData<AdvisorAppointmentCard?> get() = _appointmentDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _isCancelled = MutableLiveData<Boolean>()
    val isCancelled: LiveData<Boolean> get() = _isCancelled

    private val _showCancelDialog = MutableLiveData<Boolean>()
    val showCancelDialog: LiveData<Boolean> get() = _showCancelDialog

    fun loadAppointmentDetails(appointmentId: Long) {
        _isLoading.value = true
        viewModelScope.launch {
            val appointmentResult = appointmentRepository.getAppointmentById(appointmentId, GlobalVariables.TOKEN)

            if (appointmentResult is Resource.Success && appointmentResult.data != null) {
                val appointment = appointmentResult.data

                val advisorResult = advisorRepository.searchAdvisorByAdvisorId(appointment.advisorId, GlobalVariables.TOKEN)
                val advisorName = if (advisorResult is Resource.Success) {
                    val advisor = advisorResult.data
                    val profileResult = advisor?.userId?.let { userId ->
                        profileRepository.searchProfile(userId, GlobalVariables.TOKEN)
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
                        profileRepository.searchProfile(userId, GlobalVariables.TOKEN)
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

                _appointmentDetails.value = AdvisorAppointmentCard(
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

                _isLoading.value = false

            } else if (appointmentResult is Resource.Error) {
                _isLoading.value = false
                _errorMessage.value = "Error al obtener los detalles de la cita"
            }
        }
    }

    fun goBack() {
        navController.popBackStack()
    }

    fun onCancelAppointmentClick() {
        _showCancelDialog.value = true
    }

    fun cancelAppointment(appointmentId: Long, cancelReason: String) {
        _isLoading.value = true
        viewModelScope.launch {

            val result = appointmentRepository.deleteAppointment(appointmentId, GlobalVariables.TOKEN)

            if (result is Resource.Success) {


                _isCancelled.value = true

                navController.navigate(Routes.CancelAppointmentConfirmation.route)


                // Aumentar una notificacion luego de eliminar la cita

            } else if (result is Resource.Error) {
                _errorMessage.value = "Error al cancelar la cita"
            }

        }
    }

    fun onDismissCancelDialog() {
        _showCancelDialog.value = false
    }


}
