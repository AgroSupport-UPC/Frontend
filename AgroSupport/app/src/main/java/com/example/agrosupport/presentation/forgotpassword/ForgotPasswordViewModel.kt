package com.example.agrosupport.presentation.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.agrosupport.common.Routes
import com.example.agrosupport.data.repository.LoginRepository

class ForgotPasswordViewModel(
    private val navController: NavController
) : ViewModel() {

    fun goToLoginScreen() {
        navController.navigate(Routes.SignIn.route)
    }

    fun goToRestorePasswordScreen() {
        navController.navigate(Routes.RestorePassword.route)
    }

    fun goBack() {
        navController.popBackStack()
    }
}