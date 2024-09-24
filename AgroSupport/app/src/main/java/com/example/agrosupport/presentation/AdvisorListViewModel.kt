package com.example.agrosupport.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.agrosupport.common.Constants
import com.example.agrosupport.common.Resource
import com.example.agrosupport.common.Routes
import com.example.agrosupport.common.UIState
import com.example.agrosupport.data.repository.AdvisorRepository
import com.example.agrosupport.data.repository.ProfileRepository

class AdvisorListViewModel(private val navController: NavController, private val profileRepository: ProfileRepository,
                           private val advisorRepository: AdvisorRepository): ViewModel() {

    private val _state = mutableStateOf(UIState<List<AdvisorCard>>())
    val state: State<UIState<List<AdvisorCard>>> get() = _state

    fun goBack() {
        navController.popBackStack()
    }

    fun getAdvisorList() {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            val result = profileRepository.getAdvisorList(Constants.EXAMPLE_TOKEN)
            if (result is Resource.Success) {
                val profiles = result.data
                if (profiles != null) {
                    val advisorCards = mutableListOf<AdvisorCard>()
                    for (profile in profiles) {
                        val advisorResult = advisorRepository.searchAdvisorByUserId(profile.userId, Constants.EXAMPLE_TOKEN)
                        if (advisorResult is Resource.Success) {
                            val advisorId = advisorResult.data?.id ?: 0 // Asigna 0 si el id es null
                            val rating = advisorResult.data?.rating ?: 0.0 // Asigna 0.0 si el rating es null
                            advisorCards.add(
                                AdvisorCard(
                                    id = advisorId,
                                    name = profile.firstName + " " + profile.lastName,
                                    rating = rating,
                                    link = profile.photo
                                )
                            )
                        } else {
                            // Manejo de error para el rating, puedes decidir cómo proceder aquí
                            val advisorId = advisorResult.data?.id ?: 0
                            advisorCards.add(
                                AdvisorCard(
                                    id = advisorId,
                                    name = profile.firstName + " " + profile.lastName,
                                    rating = 0.0, // Asigna 0.0 en caso de error
                                    link = profile.photo
                                )
                            )
                        }
                    }

                    _state.value = UIState(data = advisorCards) // Actualiza el estado con la lista de AdvisorCard
                } else {
                    _state.value = UIState(message = "No advisors found")
                }
            } else if (result is Resource.Error) {
                _state.value = UIState(message = "Error retrieving advisor list")
            }
        }
    }

    fun goToAdvisorProfile(advisorId: Long) {
        navController.navigate(Routes.AdvisorDetail.route + "/$advisorId")
    }

}