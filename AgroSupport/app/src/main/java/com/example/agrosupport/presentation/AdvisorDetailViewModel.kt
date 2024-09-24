package com.example.agrosupport.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrosupport.common.Constants
import com.example.agrosupport.common.Resource
import com.example.agrosupport.common.UIState
import com.example.agrosupport.data.repository.AdvisorRepository
import com.example.agrosupport.data.repository.ProfileRepository
import kotlinx.coroutines.launch

class AdvisorDetailViewModel(private val navController: NavController, private val profileRepository: ProfileRepository,
                             private val advisorRepository: AdvisorRepository): ViewModel() {

    private val _state = mutableStateOf(UIState<AdvisorDetail>())
    val state: State<UIState<AdvisorDetail>> get() = _state

    fun goBack() {
        navController.popBackStack()
    }

    fun getAdvisorDetail(userId: Long) {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            // obtener advisor user_id a partir de la ruta "AdvisorDetail/{userId}"
            val result = profileRepository.searchProfile(userId, Constants.EXAMPLE_TOKEN)
            if (result is Resource.Success) {
                val advisor = result.data
                if (advisor != null) {
                    val ratingResult = advisorRepository.searchAdvisor(userId, Constants.EXAMPLE_TOKEN)
                    if (ratingResult is Resource.Success) {
                        val rating = ratingResult.data?.rating ?: 0.0 // Asigna 0.0 si el rating es null
                        val advisorDetail = AdvisorDetail(
                            id = advisor.id,
                            name = advisor.firstName + " " + advisor.lastName,
                            description = advisor.description,
                            occupation = advisor.occupation,
                            experience = advisor.experience,
                            rating = rating,
                            link = advisor.photo
                        )
                        _state.value = UIState(data = advisorDetail) // Actualiza el estado con el AdvisorDetail
                    }
                } else {
                    _state.value = UIState(message = "Advisor not found")
                }
            } else {
                _state.value = UIState(message = "Error while getting advisor")
            }
        }
    }
}