// MainActivity.kt
package com.example.agrosupport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.agrosupport.common.Constants
import com.example.agrosupport.common.Routes
import com.example.agrosupport.data.local.AppDatabase
import com.example.agrosupport.data.remote.*
import com.example.agrosupport.data.repository.*
import com.example.agrosupport.presentation.advisordetail.AdvisorDetailScreen
import com.example.agrosupport.presentation.advisordetail.AdvisorDetailViewModel
import com.example.agrosupport.presentation.advisorlist.AdvisorListScreen
import com.example.agrosupport.presentation.advisorlist.AdvisorListViewModel
import com.example.agrosupport.presentation.farmerappointmentdetail.CancelAppointmentSuccessScreen
import com.example.agrosupport.presentation.farmerappointmentdetail.FarmerAppointmentDetailScreen
import com.example.agrosupport.presentation.farmerappointmentdetail.FarmerAppointmentDetailViewModel
import com.example.agrosupport.presentation.confirmcreationaccountfarmer.ConfirmCreationAccountFarmerScreen
import com.example.agrosupport.presentation.confirmcreationaccountfarmer.ConfirmCreationAccountFarmerViewModel
import com.example.agrosupport.presentation.createaccountfarmer.CreateAccountFarmerScreen
import com.example.agrosupport.presentation.createaccountfarmer.CreateAccountFarmerViewModel
import com.example.agrosupport.presentation.createprofilefarmer.CreateProfileFarmerScreen
import com.example.agrosupport.presentation.createprofilefarmer.CreateProfileFarmerViewModel
import com.example.agrosupport.presentation.exploreposts.ExplorePostsScreen
import com.example.agrosupport.presentation.exploreposts.ExplorePostsViewModel
import com.example.agrosupport.presentation.farmerappointments.FarmerAppointmentListScreen
import com.example.agrosupport.presentation.farmerappointments.FarmerAppointmentListViewModel
import com.example.agrosupport.presentation.farmerhistory.FarmerAppointmentHistoryListScreen
import com.example.agrosupport.presentation.farmerhistory.FarmerAppointmentHistoryListViewModel
import com.example.agrosupport.presentation.farmerhome.FarmerHomeScreen
import com.example.agrosupport.presentation.farmerhome.FarmerHomeViewModel
import com.example.agrosupport.presentation.forgotpassword.ForgotPasswordScreen
import com.example.agrosupport.presentation.forgotpassword.ForgotPasswordViewModel
import com.example.agrosupport.presentation.login.LoginScreen
import com.example.agrosupport.presentation.login.LoginViewModel
import com.example.agrosupport.presentation.newappointment.NewAppointmentScreen
import com.example.agrosupport.presentation.newappointment.NewAppointmentSuccessScreen
import com.example.agrosupport.presentation.newappointment.NewAppointmentViewModel
import com.example.agrosupport.presentation.notificationlist.NotificationListScreen
import com.example.agrosupport.presentation.notificationlist.NotificationListViewModel
import com.example.agrosupport.presentation.rating.FarmerReviewAppointmentScreen
import com.example.agrosupport.presentation.rating.FarmerReviewAppointmentViewModel
import com.example.agrosupport.presentation.restorepassword.RestorePasswordScreen
import com.example.agrosupport.presentation.restorepassword.RestorePasswordViewModel
import com.example.agrosupport.presentation.reviewlist.ReviewListScreen
import com.example.agrosupport.presentation.reviewlist.ReviewListViewModel
import com.example.agrosupport.presentation.signup.CreateAccountScreen
import com.example.agrosupport.presentation.signup.CreateAccountViewModel
import com.example.agrosupport.presentation.welcomesection.WelcomeScreen
import com.example.agrosupport.presentation.welcomesection.WelcomeViewModel
import com.example.agrosupport.ui.theme.AgroSupportTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val userDao = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "agrosupport-db")
            .build()
            .getUserDao()

        val authenticationService = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthenticationService::class.java)

        val profileService = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(ProfileService::class.java)
        val advisorService = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(AdvisorService::class.java)
        val farmerService = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(FarmerService::class.java)
        val reviewService = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(ReviewService::class.java)
        val appointmentService = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(AppointmentService::class.java)
        val availableDateService = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(AvailableDateService::class.java)
        val notificationService = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(NotificationService::class.java)
        val postService = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(PostService::class.java)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgroSupportTheme {
                val navController = rememberNavController()
                val welcomeViewModel = WelcomeViewModel(navController, AuthenticationRepository(authenticationService, userDao))
                val loginViewModel = LoginViewModel(navController, AuthenticationRepository(authenticationService, userDao))
                val forgotPasswordViewModel = ForgotPasswordViewModel(navController)
                val farmerHomeViewModel = FarmerHomeViewModel(navController, ProfileRepository(profileService), AuthenticationRepository(authenticationService, userDao), AppointmentRepository(appointmentService), FarmerRepository(farmerService), AdvisorRepository(advisorService))
                val restorePasswordViewModel = RestorePasswordViewModel(navController)
                val advisorListViewModel = AdvisorListViewModel(navController, ProfileRepository(profileService), AdvisorRepository(advisorService))
                val advisorDetailViewModel = AdvisorDetailViewModel(navController, ProfileRepository(profileService), AdvisorRepository(advisorService))
                val newAppointmentViewModel = NewAppointmentViewModel(navController, AvailableDateRepository(availableDateService), AppointmentRepository(appointmentService))
                val reviewListViewModel = ReviewListViewModel(navController, ReviewRepository(reviewService), ProfileRepository(profileService), FarmerRepository(farmerService))
                val farmerAppointmentListViewModel = FarmerAppointmentListViewModel(navController, ProfileRepository(profileService), AdvisorRepository(advisorService), AppointmentRepository(appointmentService), FarmerRepository(farmerService))
                val farmerAppointmentHistoryListViewModel = FarmerAppointmentHistoryListViewModel(navController, ProfileRepository(profileService), AdvisorRepository(advisorService), AppointmentRepository(appointmentService), FarmerRepository(farmerService))
                val farmerAppointmentDetailViewModel = FarmerAppointmentDetailViewModel(navController, AppointmentRepository(appointmentService), AdvisorRepository(advisorService), ProfileRepository(profileService), ReviewRepository(reviewService))
                val farmerReviewAdvisorViewModel = FarmerReviewAppointmentViewModel(navController, ReviewRepository(reviewService), AppointmentRepository(appointmentService), AdvisorRepository(advisorService), ProfileRepository(profileService))
                val createAccountViewModel = CreateAccountViewModel(navController)
                val createAccountFarmerPart1ViewModel = CreateAccountFarmerViewModel(navController, AuthenticationRepository(authenticationService, userDao))
                val createProfileFarmerViewModel = CreateProfileFarmerViewModel(navController, ProfileRepository(profileService), createAccountFarmerPart1ViewModel)
                val confirmCreationAccountFarmerViewModel = ConfirmCreationAccountFarmerViewModel(navController)
                val notificationListViewModel = NotificationListViewModel(navController, NotificationRepository(notificationService))
                val explorePostsViewModel = ExplorePostsViewModel(navController, PostRepository(postService), ProfileRepository(profileService), AdvisorRepository(advisorService))
                NavHost(navController = navController, startDestination = Routes.Welcome.route) {
                    composable(route = Routes.Welcome.route) {
                        WelcomeScreen(viewModel = welcomeViewModel)
                    }
                    composable(route = Routes.SignIn.route) {
                        LoginScreen(viewModel = loginViewModel)
                    }
                    composable(route = Routes.ForgotPassword.route) {
                        ForgotPasswordScreen(viewModel = forgotPasswordViewModel)
                    }
                    composable(route = Routes.RestorePassword.route) {
                        RestorePasswordScreen(viewModel = restorePasswordViewModel)
                    }
                    composable(route = Routes.FarmerHome.route) {
                        FarmerHomeScreen(viewModel = farmerHomeViewModel)
                    }
                    composable(route = Routes.AdvisorList.route) {
                        AdvisorListScreen(viewModel = advisorListViewModel)
                    }
                    composable(route = Routes.AdvisorDetail.route + "/{advisorId}") { backStackEntry ->
                        val advisorId = backStackEntry.arguments?.getString("advisorId")?.toLong() ?: 0
                        AdvisorDetailScreen(viewModel = advisorDetailViewModel, advisorId = advisorId)
                    }
                    composable(route = Routes.ReviewList.route + "/{advisorId}") {
                        val advisorId = it.arguments?.getString("advisorId")?.toLong() ?: 0
                        ReviewListScreen(viewModel = reviewListViewModel, advisorId = advisorId)
                    }
                    composable(route = Routes.NewAppointment.route + "/{advisorId}") {
                        val advisorId = it.arguments?.getString("advisorId")?.toLong() ?: 0
                        NewAppointmentScreen(viewModel = newAppointmentViewModel, advisorId = advisorId)
                    }
                    composable(route = Routes.NewAppointmentConfirmation.route) {
                        NewAppointmentSuccessScreen {
                            navController.navigate(Routes.FarmerAppointmentList.route)
                        }
                    }
                    composable(route = Routes.FarmerAppointmentList.route) {
                        FarmerAppointmentListScreen(viewModel = farmerAppointmentListViewModel)
                    }
                    composable(route = Routes.FarmerAppointmentHistory.route) {
                        FarmerAppointmentHistoryListScreen(viewModel = farmerAppointmentHistoryListViewModel)
                    }
                    composable(route = Routes.FarmerAppointmentDetail.route + "/{appointmentId}") {
                        val appointmentId = it.arguments?.getString("appointmentId")?.toLong() ?: 0
                        FarmerAppointmentDetailScreen(viewModel = farmerAppointmentDetailViewModel, appointmentId = appointmentId)
                    }
                    composable(route = Routes.CancelAppointmentConfirmation.route) {
                        CancelAppointmentSuccessScreen(onBackClick = {
                            navController.navigate(Routes.FarmerAppointmentList.route)
                        })
                    }
                    composable(route = Routes.FarmerReviewAppointment.route + "/{appointmentId}") {
                        val appointmentId = it.arguments?.getString("appointmentId")?.toLong() ?: 0
                        FarmerReviewAppointmentScreen(viewModel = farmerReviewAdvisorViewModel, appointmentId = appointmentId)
                    }
                    composable(route = Routes.SignUp.route) {
                        CreateAccountScreen(viewModel = createAccountViewModel)
                    }
                    composable(route = Routes.CreateAccountFarmer.route) {
                        CreateAccountFarmerScreen(viewModel = createAccountFarmerPart1ViewModel)
                    }
                    composable(route = Routes.CreateProfileFarmer.route) {
                        CreateProfileFarmerScreen(viewModel = createProfileFarmerViewModel)
                    }
                    composable(route = Routes.ConfirmCreationAccountFarmer.route) {
                        ConfirmCreationAccountFarmerScreen(viewModel = confirmCreationAccountFarmerViewModel)
                    }
                    composable(route = Routes.NotificationList.route) {
                        NotificationListScreen(viewModel = notificationListViewModel)
                    }
                    composable(route = Routes.ExplorePosts.route) {
                        ExplorePostsScreen(viewModel = explorePostsViewModel)
                    }
                }
            }
        }
    }
}