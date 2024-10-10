package com.example.agrosupport.presentation.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.agrosupport.common.Routes
import com.example.agrosupport.common.UIState

class ForgotPasswordViewModel(
    private val navController: NavController
) : ViewModel() {

    private val _state = MutableLiveData<UIState<Unit>>(UIState())
    val state: LiveData<UIState<Unit>> get() = _state

    fun validateEmail(email: String) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.value = UIState(message = "Correo electrónico no válido")
        } else {
            _state.value = UIState(isLoading = true)
            goToRestorePasswordScreen()
        }
    }

    fun clearError() {
        _state.value = _state.value?.copy(message = "")
    }

    private fun goToRestorePasswordScreen() {
        navController.navigate(Routes.RestorePassword.route)
        _state.value = UIState(isLoading = false)
    }

    fun goToLoginScreen() {
        navController.navigate(Routes.SignIn.route)
    }

    fun goBack() {
        navController.popBackStack()
    }
}