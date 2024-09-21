package com.example.agrosupport.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.agrosupport.common.Constants
import com.example.agrosupport.common.Resource
import com.example.agrosupport.common.UIState
import com.example.agrosupport.data.repository.AdvisorRepository
import com.example.agrosupport.data.repository.ProfileRepository
import com.example.agrosupport.presentation.AdvisorCard

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
                val advisors = result.data
                if (advisors != null) {
                    val advisorCards = mutableListOf<AdvisorCard>()
                    for (advisor in advisors) {
                        val ratingResult = advisorRepository.searchAdvisor(advisor.userId, Constants.EXAMPLE_TOKEN)
                        if (ratingResult is Resource.Success) {
                            val rating = ratingResult.data?.rating ?: 0.0 // Asigna 0.0 si el rating es null
                            advisorCards.add(
                                AdvisorCard(
                                    id = advisor.id,
                                    name = advisor.firstName + " " + advisor.lastName,
                                    rating = rating,
                                    link = advisor.photo
                                )
                            )
                        } else {
                            // Manejo de error para el rating, puedes decidir cómo proceder aquí
                            advisorCards.add(
                                AdvisorCard(
                                    id = advisor.id,
                                    name = advisor.firstName + " " + advisor.lastName,
                                    rating = 0.0, // Asigna 0.0 en caso de error
                                    link = advisor.photo
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

}