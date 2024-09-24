package com.example.agrosupport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.agrosupport.common.Constants
import com.example.agrosupport.common.Routes
import com.example.agrosupport.data.remote.AdvisorService
import com.example.agrosupport.data.remote.ProfileService
import com.example.agrosupport.data.repository.AdvisorRepository
import com.example.agrosupport.data.repository.ProfileRepository
import com.example.agrosupport.presentation.AdvisorDetailScreen
import com.example.agrosupport.presentation.AdvisorDetailViewModel
import com.example.agrosupport.presentation.AdvisorListScreen
import com.example.agrosupport.presentation.AdvisorListViewModel
import com.example.agrosupport.presentation.FarmerHomeScreen
import com.example.agrosupport.presentation.FarmerHomeViewModel
import com.example.agrosupport.ui.theme.AgroSupportTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val profileService = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileService::class.java)

        val advisorService = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdvisorService::class.java)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgroSupportTheme {
                val navController = rememberNavController()
                val farmerHomeViewModel = FarmerHomeViewModel(navController, ProfileRepository(profileService))
                val advisorListViewModel = AdvisorListViewModel(navController, ProfileRepository(profileService), AdvisorRepository(advisorService))
                val advisorDetailViewModel = AdvisorDetailViewModel(navController, ProfileRepository(profileService), AdvisorRepository(advisorService))
                NavHost(navController = navController, startDestination = Routes.FarmerHome.route) {
                    composable(route = Routes.FarmerHome.route) {
                        FarmerHomeScreen(viewModel = farmerHomeViewModel)
                    }
                    composable(route = Routes.AdvisorList.route) {
                        AdvisorListScreen(viewModel = advisorListViewModel)
                    }
                    composable(route = Routes.AdvisorDetail.route + "/{userId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId")?.toLong() ?: 0
                        AdvisorDetailScreen(viewModel = advisorDetailViewModel, userId = userId)
                    }
                }
            }
        }
    }
}

