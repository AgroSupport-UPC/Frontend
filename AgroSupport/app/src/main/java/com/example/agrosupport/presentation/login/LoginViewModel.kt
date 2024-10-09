package com.example.agrosupport.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrosupport.common.GlobalVariables
import com.example.agrosupport.common.Routes
import com.example.agrosupport.data.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val navController: NavController,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun signIn(username: String, password: String) {
        viewModelScope.launch {
            loginRepository.signIn(username, password) { result ->
                result.onSuccess { loginResponse ->
                    // Almacenar el userId en Constants (o en SharedPreferences)
                    GlobalVariables.USER_ID = loginResponse.id
                    GlobalVariables.TOKEN = loginResponse.token
                    // Navegación después de un inicio de sesión exitoso
                    goToFarmerScreen()
                }.onFailure { exception ->
                    // Manejo de error
                    val message = exception.message ?: "Error desconocido"
                    showError("Correo y/o contraseña incorrectos / $message")
                }
            }
        }
    }

    private fun goToFarmerScreen() {
        navController.navigate(Routes.FarmerHome.route)
    }

    private fun showError(message: String) {
        _errorMessage.value = message
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
