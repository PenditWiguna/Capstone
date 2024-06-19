package com.ratan.maigen.data.api

import com.ratan.maigen.data.response.DestinationDetailResponse
import com.ratan.maigen.data.response.DestinationResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiServiceModel {

    @GET("/category-recommendation")
    fun getRecommendations(
        @Header("Authorization") token: String,
        @Query("category") category: String
    ): DestinationResponse

    @GET("/place-recommendation")
    suspend fun getSimilarDestinations(
        @Header("Authorization") token: String,
        @Query("place_id") placeId: Int
    ): DestinationResponse

    @GET("/place-detail")
    suspend fun getDestinationDetail(
        @Header("Authorization") token: String,
        @Query("place_id") placeId: Int
    ): DestinationDetailResponse
}