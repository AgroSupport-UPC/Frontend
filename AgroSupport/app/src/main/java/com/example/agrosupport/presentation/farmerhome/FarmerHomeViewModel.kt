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
import com.example.agrosupport.data.local.AppDatabase
import com.example.agrosupport.data.repository.AuthenticationRepository
import com.example.agrosupport.data.repository.ProfileRepository
import com.example.agrosupport.domain.Profile
import com.example.agrosupport.domain.AuthenticationResponse

class FarmerHomeViewModel(
    private val navController: NavController,
    private val profileRepository: ProfileRepository,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {
    private val _state = mutableStateOf(UIState<Profile>())
    val state: State<UIState<Profile>> get() = _state

    private val _expanded = mutableStateOf(false)
    val expanded: State<Boolean> get() = _expanded


    fun getFarmerName() {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            val result = profileRepository.searchProfile(GlobalVariables.USER_ID, GlobalVariables.TOKEN)
            if (result is Resource.Success) {
                _state.value = UIState(data = result.data)
            } else {
                _state.value = UIState(message = "Error getting profile")
            }
        }
    }

    fun signOut() {
        GlobalVariables.ROLES = emptyList()
        viewModelScope.launch {
            val authResponse = AuthenticationResponse(
                id = GlobalVariables.USER_ID,
                username = "",
                token = GlobalVariables.TOKEN
            )
            authenticationRepository.deleteUser(authResponse)
            goToWelcomeSection()
        }
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

    fun goToWelcomeSection() {
        navController.navigate(Routes.Welcome.route)
    }
}