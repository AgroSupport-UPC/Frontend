package com.example.agrosupport.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrosupport.common.GlobalVariables
import com.example.agrosupport.common.Resource
import com.example.agrosupport.common.Routes
import com.example.agrosupport.common.UIState
import com.example.agrosupport.data.repository.authentication.AuthenticationRepository
import com.example.agrosupport.domain.authentication.AuthenticationResponse
import kotlinx.coroutines.launch

class LoginViewModel(
    private val navController: NavController,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _state = mutableStateOf(UIState<Unit>())
    val state: State<UIState<Unit>> get() = _state

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    private val _isPasswordVisible = mutableStateOf(false)
    val isPasswordVisible: State<Boolean> get() = _isPasswordVisible

    fun signIn() {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            when (val result = authenticationRepository.signIn(_email.value, _password.value)) {
                is Resource.Success -> {
                    GlobalVariables.TOKEN = result.data?.token ?: ""
                    GlobalVariables.USER_ID = result.data?.id ?: 0

                    _state.value = UIState(isLoading = false)

                    if (GlobalVariables.TOKEN.isNotBlank() && GlobalVariables.USER_ID != 0L) {
                        //Se guarda el usuario en la base de datos local para loguearlo automaticamente
                        authenticationRepository.insertUser(
                            AuthenticationResponse(
                                id = GlobalVariables.USER_ID,
                                username = _email.value,
                                token = GlobalVariables.TOKEN
                            )
                        )
                        _email.value = ""
                        _password.value = ""
                        goToFarmerScreen()
                    } else {
                        _state.value = UIState(message = "Error al iniciar sesión")
                    }
                }
                is Resource.Error -> {
                    _state.value = UIState(message = result.message.toString())
                }
            }
        }
    }

    fun clearError() {
        _state.value = UIState(message = "")
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun goToForgotPasswordScreen() {
        navController.navigate(Routes.ForgotPassword.route)
    }

    private fun goToFarmerScreen() {
        navController.navigate(Routes.FarmerHome.route)
    }

    fun goToSignUpScreen() {
        navController.navigate(Routes.SignUp.route)
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }
}