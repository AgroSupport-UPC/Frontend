package com.example.agrosupport.presentation.welcomesection

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.agrosupport.common.Routes


class WelcomeViewModel(private val navController: NavController): ViewModel()  {

    fun goToLoginScreen() {
        navController.navigate(Routes.SignIn.route)
    }

    fun goToFarmerHomeScreen() {
        navController.navigate(Routes.FarmerHome.route)
    }

}