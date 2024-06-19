package com.ratan.maigen.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ratan.maigen.data.model.UserModel
import com.ratan.maigen.data.repository.DestinationRepository
import com.ratan.maigen.data.repository.RecommendationRepository
import com.ratan.maigen.data.response.ListDestinationItem
import kotlinx.coroutines.launch

class MainViewModel(private val destinationRepository: DestinationRepository) : ViewModel() {

    fun register(username: String, email: String, password: String) =
        destinationRepository.register(username, email, password)

    fun login(email: String, password: String) = destinationRepository.login(email, password)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            destinationRepository.saveSession(user)
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