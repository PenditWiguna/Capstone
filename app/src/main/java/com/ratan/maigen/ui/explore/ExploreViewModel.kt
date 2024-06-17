package com.ratan.maigen.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratan.maigen.data.api.ApiConfig
import com.ratan.maigen.data.response.ExploreResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreViewModel : ViewModel() {

    private val _exploreData = MutableLiveData<List<ExploreResult>>()
    val exploreData: LiveData<List<ExploreResult>> = _exploreData

    private val apiService = ApiConfig.getApiService()

//    fun searchDestination(query: String) {
//        viewModelScope.launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    apiService.explore("Bearer TOKEN", query) // Gantilah "Bearer TOKEN" dengan token yang sesuai
//                }
//                if (response != null) {
//                    if (response.isSuccessful) {
//                        _exploreData.value = response.body()?.results ?: emptyList()
//                    } else {
//                        _exploreData.value = emptyList()
//                    }
//                } else {
//                    _exploreData.value = emptyList()
//                }
//            } catch (e: Exception) {
//                // Tangani kesalahan saat mengakses respon
//                _exploreData.value = emptyList()
//                e.printStackTrace()
//            }
//        }
//    }
}