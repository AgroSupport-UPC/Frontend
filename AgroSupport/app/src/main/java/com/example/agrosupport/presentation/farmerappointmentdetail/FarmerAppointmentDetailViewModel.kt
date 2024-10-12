package com.example.agrosupport.presentation.appointmentdetails

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
import com.example.agrosupport.common.Constants
import com.example.agrosupport.common.GlobalVariables

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

    // Método para cargar los detalles de la cita por su ID
    fun loadAppointmentDetails(appointmentId: Long) {
        _isLoading.value = true
        viewModelScope.launch {
            // Obtener los detalles de la cita
            val appointmentResult = appointmentRepository.getAppointmentById(appointmentId, GlobalVariables.TOKEN)

            if (appointmentResult is Resource.Success && appointmentResult.data != null) {
                val appointment = appointmentResult.data

                // Buscar información del asesor
                val advisorResult = advisorRepository.searchAdvisorByAdvisorId(appointment.advisorId, GlobalVariables.TOKEN)
                val advisorName = if (advisorResult is Resource.Success) {
                    val advisor = advisorResult.data
                    // Obtener información del perfil del asesor
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

                // Obtener foto del asesor
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

                // Crear el objeto AdvisorAppointmentCard con los detalles completos
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

    // Método para cancelar la cita seleccionada
    fun cancelAppointment(appointmentId: Long) {
        _isLoading.value = true
        viewModelScope.launch {

        }
    }

    // Método para limpiar el mensaje de error
    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun goBack() {
        navController.popBackStack()
    }

    fun onCancelAppointmentClick() {

    }

}
