package com.example.agrosupport.presentation.reviewlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.agrosupport.common.Constants
import com.example.agrosupport.common.Resource
import com.example.agrosupport.common.UIState
import com.example.agrosupport.data.repository.AdvisorRepository
import com.example.agrosupport.data.repository.FarmerRepository
import com.example.agrosupport.data.repository.ProfileRepository
import com.example.agrosupport.data.repository.ReviewRepository
import kotlinx.coroutines.launch

class ReviewListViewModel(private val navController: NavController, private val reviewRepository: ReviewRepository,
                          private val profileRepository: ProfileRepository, val advisorRepository: AdvisorRepository,
                          val farmerRepository: FarmerRepository): ViewModel() {
    private val _state = mutableStateOf(UIState<List<ReviewCard>>())
    val state: State<UIState<List<ReviewCard>>> get() = _state

    fun goBack() {
        navController.popBackStack()
    }

    fun getAdvisorReviewList(advisorId: Long) {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            val result = reviewRepository.getAdvisorReviewsList(advisorId, Constants.EXAMPLE_TOKEN)
            if (result is Resource.Success) {
                val reviews = result.data
                if (reviews != null) {
                    val reviewCards = mutableListOf<ReviewCard>()
                    for (review in reviews) {
                        val farmerResult = farmerRepository.searchFarmerByFarmerId(review.farmerId, Constants.EXAMPLE_TOKEN)
                        if (farmerResult is Resource.Success) {
                            val farmer = farmerResult.data
                            if (farmer != null) {
                                val farmerProfileResult = profileRepository.searchProfile(farmer.userId, Constants.EXAMPLE_TOKEN)
                                if (farmerProfileResult is Resource.Success) {
                                    val profile = farmerProfileResult.data
                                    if (profile != null) {
                                        reviewCards.add(
                                            ReviewCard(
                                                id = review.id,
                                                farmerName = profile.firstName + " " + profile.lastName,
                                                comment = review.comment,
                                                rating = review.rating,
                                                farmerLink = profile.photo
                                            )
                                        )
                                    }
                                    else {
                                        reviewCards.add(
                                            ReviewCard(
                                                id = review.id,
                                                farmerName = "Anónimo",
                                                comment = review.comment,
                                                rating = review.rating,
                                                farmerLink = ""
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                    _state.value = UIState(data = reviewCards)
                } else {
                    _state.value = UIState(message = "No reviews for this advisor found")
                }
            } else {
                _state.value = UIState(message = "Error while getting advisor reviews")
            }
        }
    }

}