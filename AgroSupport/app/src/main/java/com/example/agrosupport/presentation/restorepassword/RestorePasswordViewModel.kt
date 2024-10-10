package com.example.agrosupport.presentation.restorepassword

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.agrosupport.common.Routes

class RestorePasswordViewModel(
    private val navController: NavController
) : ViewModel() {


    fun goToLoginScreen() {
        navController.navigate(Routes.SignIn.route)
    }

    fun goBack() {
        navController.popBackStack()
    }
}