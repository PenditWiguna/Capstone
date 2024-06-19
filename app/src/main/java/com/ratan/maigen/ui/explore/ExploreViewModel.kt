package com.ratan.maigen.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ratan.maigen.data.api.ApiConfig
import com.ratan.maigen.data.response.ListExploreResult
import kotlinx.coroutines.launch

class ExploreViewModel : ViewModel() {

    private val _exploreData = MutableLiveData<List<ListExploreResult>>()
    val exploreData: LiveData<List<ListExploreResult>> = _exploreData

    private val _filteredData = MutableLiveData<List<ListExploreResult>>()
    val filteredData: LiveData<List<ListExploreResult>> get() = _filteredData

    init {
        loadData()
    }

    private val apiService = ApiConfig.getApiServiceMobile()


    private fun loadData() {
        // Simulate loading data from an API or database
        viewModelScope.launch {
            val data = listOf<ListExploreResult>() // Replace with actual data loading
            _exploreData.value = data
            _filteredData.value = data
        }
    }

    fun searchDestination(query: String) {
        val filteredList = _exploreData.value?.filter {
            (it.Place_Name?.contains(query, ignoreCase = true) ?: false) ||
                    (it.Description?.contains(query, ignoreCase = true) ?: false) ||
                    (it.City?.contains(query, ignoreCase = true) ?: false)
        } ?: emptyList()
        _filteredData.value = filteredList
    }



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