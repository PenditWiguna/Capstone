package com.ratan.maigen.data.di

import android.content.Context
import com.ratan.maigen.data.api.ApiConfig
import com.ratan.maigen.data.preferences.UserPreferences
import com.ratan.maigen.data.preferences.dataStore
import com.ratan.maigen.data.repository.DestinationRepository

object Injection {
    fun provideRepository(context: Context): DestinationRepository {
        val apiService = ApiConfig.getApiService()
        val pref = UserPreferences.getInstance(context.dataStore)
        return DestinationRepository.getInstance(apiService, pref)
    }
}