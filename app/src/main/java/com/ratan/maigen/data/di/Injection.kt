package com.ratan.maigen.data.di

import android.content.Context
import com.ratan.maigen.data.api.ApiConfig
import com.ratan.maigen.data.preferences.UserPreferences
import com.ratan.maigen.data.preferences.dataStore
import com.ratan.maigen.data.repository.DestinationRepository
import com.ratan.maigen.data.repository.RecommendationRepository

object Injection {
    fun provideRepositoryMobile(context: Context): DestinationRepository {
        val apiServiceMobile = ApiConfig.getApiServiceMobile()
        val pref = UserPreferences.getInstance(context.dataStore)
        return DestinationRepository.getInstance(apiServiceMobile, pref)
    }

    fun provideRepositoryModel(context: Context): RecommendationRepository {
        val apiServiceModel = ApiConfig.getApiServiceModel()
        return RecommendationRepository.getInstance(apiServiceModel)
    }
}