package com.example.agrosupport.presentation.reviewlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrosupport.common.GlobalVariables
import com.example.agrosupport.common.Resource
import com.example.agrosupport.common.UIState
import com.example.agrosupport.data.repository.FarmerRepository
import com.example.agrosupport.data.repository.ProfileRepository
import com.example.agrosupport.data.repository.ReviewRepository
import kotlinx.coroutines.launch

class ReviewListViewModel(private val navController: NavController, private val reviewRepository: ReviewRepository,
                          private val profileRepository: ProfileRepository,
                          private val farmerRepository: FarmerRepository): ViewModel() {
    private val _state = mutableStateOf(UIState<List<ReviewCard>>())
    val state: State<UIState<List<ReviewCard>>> get() = _state

    fun goBack() {
        navController.popBackStack()
    }

    fun getAdvisorReviewList(advisorId: Long) {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            val result = reviewRepository.getAdvisorReviewsList(advisorId, GlobalVariables.TOKEN)

            if (result is Resource.Success) {
                val reviews = result.data ?: run {
                    _state.value = UIState(message = "No se encontraron reseñas para este asesor")
                    return@launch
                }

                val reviewCards = reviews.map { review ->
                    val farmerResult = farmerRepository.searchFarmerByFarmerId(review.farmerId, GlobalVariables.TOKEN)
                    val farmerProfile = if (farmerResult is Resource.Success) {
                        val farmer = farmerResult.data
                        if (farmer != null) {
                            val profileResult = profileRepository.searchProfile(farmer.userId, GlobalVariables.TOKEN)
                            if (profileResult is Resource.Success) profileResult.data else null
                        } else null
                    } else null

                    val farmerName = farmerProfile?.let { "${it.firstName} ${it.lastName}" } ?: "Anónimo"
                    val farmerLink = farmerProfile?.photo ?: ""

                    ReviewCard(
                        id = review.id,
                        farmerName = farmerName,
                        comment = review.comment,
                        rating = review.rating,
                        farmerLink = farmerLink
                    )
                }
                _state.value = UIState(data = reviewCards)
            } else {
                _state.value = UIState(message = "Error al intentar obtener las reseñas del asesor")
            }
        }
    }


}