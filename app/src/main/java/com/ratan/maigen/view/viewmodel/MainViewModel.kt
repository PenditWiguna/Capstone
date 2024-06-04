package com.ratan.maigen.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ratan.maigen.data.model.UserModel
import com.ratan.maigen.data.repository.DestinationRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DestinationRepository) : ViewModel() {

    // val about = repository.about

    fun register(name: String, email: String, password: String) =
        repository.register(name, email, password)

    fun login(email: String, password: String) = repository.login(email, password)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}