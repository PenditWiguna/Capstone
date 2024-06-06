package com.ratan.maigen.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ratan.maigen.data.model.UserModel
import com.ratan.maigen.data.repository.DestinationRepository
import com.ratan.maigen.data.response.ListDestinationItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DestinationRepository) : ViewModel() {

    // val about = repository.about

    fun register(name: String, email: String, password: String) =
        repository.register(name, email, password)

    fun login(email: String, password: String) = repository.login(email, password)

    fun getDestination(token: String): LiveData<PagingData<ListDestinationItem>> = repository.getDestination(token).cachedIn(viewModelScope)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}