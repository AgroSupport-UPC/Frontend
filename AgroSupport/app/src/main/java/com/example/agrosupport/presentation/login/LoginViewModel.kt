package com.example.agrosupport.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrosupport.common.GlobalVariables
import com.example.agrosupport.common.Routes
import com.example.agrosupport.common.UIState
import com.example.agrosupport.data.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val navController: NavController,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _state = MutableLiveData<UIState<Unit>>(UIState())
    val state: LiveData<UIState<Unit>> get() = _state

    fun signIn(username: String, password: String) {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            loginRepository.signIn(username, password) { result ->
                result.onSuccess { loginResponse ->
                    GlobalVariables.USER_ID = loginResponse.id
                    GlobalVariables.TOKEN = loginResponse.token
                    _state.value = UIState(data = Unit)
                    goToFarmerScreen()
                }.onFailure { exception ->
                    val message = exception.message ?: "Error desconocido"
                    _state.value = UIState(message = "Correo y/o contraseña incorrectos / $message")
                }
            }
        }
    }

    fun clearError() {
        _state.value = _state.value?.copy(message = "")
    }

    fun goToForgotPasswordScreen() {
        navController.navigate(Routes.ForgotPassword.route)
    }

    private fun goToFarmerScreen() {
        navController.navigate(Routes.FarmerHome.route)
    }
}