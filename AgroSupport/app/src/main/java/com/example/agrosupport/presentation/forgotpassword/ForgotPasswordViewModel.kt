package com.example.agrosupport.presentation.forgotpassword

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.agrosupport.common.Routes
import com.example.agrosupport.common.UIState

class ForgotPasswordViewModel(
    private val navController: NavController
) : ViewModel() {

    private val _state = mutableStateOf(UIState<Unit>())
    val state: State<UIState<Unit>> get() = _state

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    fun validateEmail() {
        if (_email.value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()) {
            _state.value = UIState(message = "Correo electrónico no válido")
        } else {
            _state.value = UIState(isLoading = true)
            try {
                goToRestorePasswordScreen()
            } catch (e: Exception) {
                _state.value = UIState(message = "Error al navegar: ${e.message}")
            }
        }
    }

    fun clearError() {
        _state.value = UIState(message = "")
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    private fun goToRestorePasswordScreen() {
        navController.navigate(Routes.RestorePassword.route)
        _state.value = UIState(isLoading = false)
    }

    fun goToLoginScreen() {
        try {
            navController.navigate(Routes.SignIn.route)
        } catch (e: Exception) {
            _state.value = UIState(message = "Error al navegar: ${e.message}")
        }
    }

    fun goBack() {
        try {
            navController.popBackStack()
        } catch (e: Exception) {
            _state.value = UIState(message = "Error al navegar: ${e.message}")
        }
    }
}