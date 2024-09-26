package com.example.agrosupport.presentation.advisordetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrosupport.common.Constants
import com.example.agrosupport.common.Resource
import com.example.agrosupport.common.Routes
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

    fun getAdvisorDetail(advisorId: Long) {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            // obtener advisor user_id a partir de la ruta "AdvisorDetail/{userId}"
            val result = advisorRepository.searchAdvisorByAdvisorId(advisorId, Constants.EXAMPLE_TOKEN)
            if (result is Resource.Success) {
                val advisor = result.data
                if (advisor != null) {
                    val profileResult = profileRepository.searchProfile(advisor.userId, Constants.EXAMPLE_TOKEN)
                    if (profileResult is Resource.Success) {
                        val profile = profileResult.data
                        if (profile != null) {
                            val advisorDetail = AdvisorDetail(
                                id = advisor.id,
                                name = profile.firstName + " " + profile.lastName,
                                description = profile.description,
                                occupation = profile.occupation,
                                experience = profile.experience,
                                rating = advisor.rating,
                                link = profile.photo
                            )
                            _state.value = UIState(data = advisorDetail)
                        }
                    } else {
                        _state.value = UIState(message = "Error while getting advisor profile")
                    }
                } else {
                    _state.value = UIState(message = "Advisor not found")
                }
            } else {
                _state.value = UIState(message = "Error while getting advisor")
            }
        }
    }

    fun goToReviewList(advisorId: Long) {
        navController.navigate(Routes.ReviewList.route + "/$advisorId")
    }
}