package com.example.agrosupport.presentation.farmerprofile

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrosupport.common.GlobalVariables
import com.example.agrosupport.common.Resource
import com.example.agrosupport.common.Routes
import com.example.agrosupport.common.UIState
import com.example.agrosupport.data.repository.profile.CloudStorageRepository
import com.example.agrosupport.data.repository.profile.ProfileRepository
import com.example.agrosupport.domain.profile.Profile
import com.example.agrosupport.domain.profile.UpdateProfile
import kotlinx.coroutines.launch


class FarmerProfileViewModel(
    private val navController: NavController,
    private val profileRepository: ProfileRepository,
    private val cloudStorageRepository: CloudStorageRepository
) : ViewModel() {

    private val _state = mutableStateOf(UIState<Profile>())
    val state: State<UIState<Profile>> get() = _state

    fun goToHome() {
        _state.value = UIState(data = null)

        navController.navigate(Routes.FarmerHome.route)
    }


    fun getFarmerProfile() {
        _state.value = UIState(isLoading = true)

        viewModelScope.launch {

            val result = profileRepository.searchProfile(GlobalVariables.USER_ID, GlobalVariables.TOKEN)
            if (result is Resource.Success) {
                _state.value = UIState(data = result.data)

            } else {
                _state.value = UIState(message = result.message ?: "Error obteniendo el perfil")
            }

        }
    }

    fun updateFarmerProfile(updatedProfile: Profile) {

        _state.value = UIState(isLoading = true)

        viewModelScope.launch {

                val validationError = validateProfileData(updatedProfile)
                if (validationError != null) {
                    _state.value = UIState(message = validationError)
                    return@launch
                }

                val updateProfile = UpdateProfile(
                    firstName = updatedProfile.firstName,
                    lastName = updatedProfile.lastName,
                    city = updatedProfile.city,
                    country = updatedProfile.country,
                    birthDate = updatedProfile.birthDate,
                    description = updatedProfile.description,
                    photo = updatedProfile.photo,
                    occupation = updatedProfile.occupation,
                    experience = updatedProfile.experience
                )

                val result = profileRepository.updateProfile(updatedProfile.id, GlobalVariables.TOKEN, updateProfile)

                if (result is Resource.Success) {
                    _state.value = UIState(data = result.data) // Estado de éxito tras la actualización
                } else {
                    _state.value = UIState(message = result.message ?: "Error actualizando el perfil")
                }


        }
    }

    private fun validateProfileData(profile: Profile): String? {
        if (profile.firstName.isBlank()) return "El nombre no puede estar vacío"
        if (profile.lastName.isBlank()) return "El apellido no puede estar vacío"
        if (profile.city.isBlank()) return "La ciudad no puede estar vacía"
        if (profile.country.isBlank()) return "El país no puede estar vacío"
        if (profile.birthDate.isBlank()) return "La fecha de nacimiento no puede estar vacía"

        // Validate date format (yyyy-MM-dd)
        val datePattern = Regex("""\d{4}-\d{2}-\d{2}""")
        if (!datePattern.matches(profile.birthDate)) return "La fecha de nacimiento debe estar en el formato yyyy-MM-dd"

        if (profile.description.isBlank()) return "La descripción no puede estar vacía"
        if (profile.photo.isBlank()) return "La foto no puede estar vacía"
        return null
    }

    fun updateProfileWithImage(imageUri: Uri, profile: Profile) {

        _state.value = UIState(isLoading = true)

        viewModelScope.launch {
            try {
                val filename = imageUri.lastPathSegment ?: "default_image_name"
                val imageUrl = cloudStorageRepository.uploadFile(filename, imageUri)
                val updatedProfile = profile.copy(photo = imageUrl)
                updateFarmerProfile(updatedProfile)
            } catch (e: Exception) {
                _state.value = UIState(message = "Error uploading image: ${e.message}")
            }
        }
    }

    fun reloadPage() {
        _state.value = UIState(data = null)
        getFarmerProfile()
    }



}


