package com.ratan.maigen.data.repository

import com.ratan.maigen.data.api.ApiServiceModel
import com.ratan.maigen.data.response.ListDestinationItem
import com.ratan.maigen.data.response.PlaceDetail

class RecommendationRepository (private val apiServiceModel: ApiServiceModel) {

    fun getRecommendations(token: String, category: String): List<ListDestinationItem> {
        val response = apiServiceModel.getRecommendations("Bearer $token", category)
        return response.listDestination
    }

    suspend fun getSimilarDestinations(token: String, placeId: Int): List<ListDestinationItem> {
        val response = apiServiceModel.getSimilarDestinations("Bearer $token", placeId)
        return response.listDestination
    }

    suspend fun getDestinationDetail(token: String, placeId: Int): PlaceDetail {
        val response = apiServiceModel.getDestinationDetail("Bearer $token", placeId)
        return response.placeDetail
    }

    companion object {
        private const val TAG = "RecommendationViewModel"
        @Volatile
        private var instance: RecommendationRepository? = null
        fun getInstance(apiServiceModel: ApiServiceModel) =
            instance ?: synchronized(this) {
                instance ?: RecommendationRepository(apiServiceModel)
            }.also { instance = it }
    }
}