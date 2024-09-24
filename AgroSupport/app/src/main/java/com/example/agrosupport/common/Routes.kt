package com.example.agrosupport.common

sealed class Routes(val route: String) {
    data object FarmerHome : Routes("FarmerHome")
    data object AdvisorList : Routes("AdvisorList")
    data object AdvisorDetail : Routes("AdvisorDetail")
    data object ReviewList : Routes("ReviewList")
}