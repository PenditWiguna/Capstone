package com.ratan.maigen.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ratan.maigen.data.api.ApiServiceModel
import com.ratan.maigen.data.paging.DestinationPagingSource
import com.ratan.maigen.data.response.ListDestinationItem
import com.ratan.maigen.data.response.PlaceDetail

class RecommendationRepository (private val apiServiceModel: ApiServiceModel) {

    fun getDestination(token: String): LiveData<PagingData<ListDestinationItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                DestinationPagingSource(apiServiceModel,"Bearer $token")
            }
        ).liveData
    }

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