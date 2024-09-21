package com.example.agrosupport.common

sealed class Routes(val route: String) {
    data object FarmerHome : Routes("FarmerHome")
    data object AdvisorList : Routes("AdvisorList")
}