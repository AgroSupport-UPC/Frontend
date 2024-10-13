package com.example.agrosupport.presentation.createaccountfarmer

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.agrosupport.common.Routes
import androidx.compose.runtime.mutableStateOf

class CreateAccountFarmerViewModel(private val navController: NavController) : ViewModel() {

    // Variables de estado para los campos de texto
    var firstName = mutableStateOf("")
        private set
    var lastName = mutableStateOf("")
        private set
    var email = mutableStateOf("")
        private set
    var birthDate = mutableStateOf("")
        private set
    var password = mutableStateOf("")
        private set
    var city = mutableStateOf("")
        private set
    var country = mutableStateOf("")
        private set

    fun goToLoginScreen() {
        navController.navigate(Routes.SignIn.route)
    }

    fun goNextPart() {
        // Aquí puedes implementar la lógica para navegar a la siguiente parte
        // navController.navigate(Routes.CreateAccountFarmerPart2.route)
    }
}
