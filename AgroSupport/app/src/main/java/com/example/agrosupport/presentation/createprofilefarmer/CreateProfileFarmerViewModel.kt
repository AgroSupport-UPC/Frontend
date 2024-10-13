package com.example.agrosupport.presentation.createprofilefarmer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.agrosupport.common.Routes

class CreateProfileFarmerViewModel(private val navController: NavController) : ViewModel() {

    // Variables de estado para los campos de texto
    var photoUrl = mutableStateOf("")
        private set
    var description = mutableStateOf("")
        private set

    fun goToLoginScreen() {
        navController.navigate(Routes.SignIn.route)
    }

    fun goToConfirmationAccountFarmerScreen() {
        navController.navigate(Routes.ConfirmCreationAccountFarmer.route)
    }
}
