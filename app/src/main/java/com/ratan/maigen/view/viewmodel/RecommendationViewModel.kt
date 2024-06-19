package com.ratan.maigen.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ratan.maigen.data.repository.RecommendationRepository
import com.ratan.maigen.data.response.ListDestinationItem
import kotlinx.coroutines.Dispatchers

class RecommendationViewModel(private val recommendationRepository: RecommendationRepository) : ViewModel() {

    //fun getDestination(token: String): LiveData<PagingData<ListDestinationItem>> = recommendationRepository.getDestination(token).cachedIn(viewModelScope)

    fun getRecommendations(token: String, category: String) = liveData(Dispatchers.IO) {
        try {
            val recommendations = recommendationRepository.getRecommendations(token, category)
            emit(recommendations)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    fun getSimilarDestinations(token: String, placeId: Int) = liveData(Dispatchers.IO) {
        try {
            val recommendations = recommendationRepository.getSimilarDestinations(token, placeId)
            emit(recommendations)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    fun getDestinationDetail(token: String, placeId: Int) = liveData(Dispatchers.IO) {
        try {
            val placeDetail = recommendationRepository.getDestinationDetail(token, placeId)
            emit(placeDetail)
        } catch (e: Exception) {
            emit(null)
        }
    }
}