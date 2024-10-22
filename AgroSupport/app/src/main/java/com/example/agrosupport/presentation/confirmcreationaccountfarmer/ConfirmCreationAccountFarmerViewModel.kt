package com.example.agrosupport.presentation.confirmcreationaccountfarmer

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.agrosupport.common.Routes

class ConfirmCreationAccountFarmerViewModel(private val navController: NavController) : ViewModel() {

    fun goToFarmerHomeScreen() {
        navController.navigate(Routes.FarmerHome.route)
    }

}