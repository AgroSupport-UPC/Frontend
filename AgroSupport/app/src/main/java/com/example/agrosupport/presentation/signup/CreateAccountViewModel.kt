package com.example.agrosupport.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.agrosupport.common.GlobalVariables
import com.example.agrosupport.common.Routes

class CreateAccountViewModel(private val navController: NavController
) : ViewModel() {

    fun goToLoginScreen() {
        navController.navigate(Routes.SignIn.route)
    }

    fun goToFormsFarmer() {
        GlobalVariables.ROLES = listOf("ROLE_USER", "ROLE_FARMER")
        navController.navigate(Routes.CreateAccountFarmer.route)
    }

    fun goToFormsAdvisor() {
        /* TODO: Implementar la navegación a la pantalla de registro de asesor */
    }
}