package com.example.agrosupport.common

sealed class Routes(val route: String) {
    data object FarmerHome : Routes("FarmerHome")
    data object AdvisorList : Routes("AdvisorList")
    data object AdvisorDetail : Routes("AdvisorDetail")
    data object ReviewList : Routes("ReviewList")
    data object NewAppointment : Routes("NewAppointment")
    data object FarmerAppointmentList : Routes("FarmerAppointmentList")
    data object FarmerAppointmentHistory : Routes("FarmerAppointmentHistory")
    data object FarmerAppointmentDetail : Routes("FarmerAppointmentDetail")
}