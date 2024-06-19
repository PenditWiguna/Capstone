package com.ratan.maigen.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ratan.maigen.data.model.UserModel
import com.ratan.maigen.data.repository.DestinationRepository
import com.ratan.maigen.data.repository.RecommendationRepository
import com.ratan.maigen.data.response.ListDestinationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val destinationRepository: DestinationRepository, private val recommendationRepository: RecommendationRepository) : ViewModel() {

    fun register(username: String, email: String, password: String) =
        destinationRepository.register(username, email, password)

    fun login(email: String, password: String) = destinationRepository.login(email, password)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            destinationRepository.saveSession(user)
        }
    }

    fun getDestination(token: String): LiveData<PagingData<ListDestinationItem>> = recommendationRepository.getDestination(token).cachedIn(viewModelScope)

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

    fun getSession(): LiveData<UserModel> {
        return destinationRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            destinationRepository.logout()
        }
    }

    fun getSurveyPreference(): LiveData<Boolean> {
        return destinationRepository.getSurveyPreference()
    }

    fun saveSurveyPreference(preference: Boolean) {
        viewModelScope.launch {
            destinationRepository.saveSurveyPreference(preference)
        }
    }
}