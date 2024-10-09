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
import com.example.agrosupport.data.repository.ProfileRepository
import com.example.agrosupport.domain.Profile

class FarmerHomeViewModel(private val navController: NavController, private val profileRepository: ProfileRepository): ViewModel() {
    private val _state = mutableStateOf(UIState<Profile>())
    val state: State<UIState<Profile>> get() = _state

    fun getFarmerName(){
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            val result = profileRepository.searchProfile(GlobalVariables.EXAMPLE_USER_ID, GlobalVariables.EXAMPLE_TOKEN)
            if(result is Resource.Success) {
                _state.value = UIState(data = result.data)
            } else {
                _state.value = UIState(message = "Error getting profile")
            }
        }

    }
    fun goToAdvisorList() {
        navController.navigate(Routes.AdvisorList.route)
    }

    fun goToAppointmentList() {
        navController.navigate(Routes.FarmerAppointmentList.route)
    }

}