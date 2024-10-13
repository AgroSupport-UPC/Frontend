package com.example.agrosupport.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.agrosupport.common.Routes

class CreateAccountViewModel(private val navController: NavController
) : ViewModel() {

    fun goToLoginScreen() {
        navController.navigate(Routes.SignIn.route)
    }

    fun goToFormsFarmer() {
        navController.navigate(Routes.CreateAccountFarmer.route)
    }

    // Sera implementado más adelante....
    fun goToFormsAdvisor() {

    }
}