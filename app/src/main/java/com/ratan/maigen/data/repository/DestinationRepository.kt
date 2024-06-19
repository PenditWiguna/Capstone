package com.ratan.maigen.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import retrofit2.HttpException
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.google.gson.Gson
import com.ratan.maigen.data.api.ApiServiceMobile
import com.ratan.maigen.data.model.UserModel
import com.ratan.maigen.data.paging.DestinationPagingSource
import com.ratan.maigen.data.preferences.UserPreferences
import com.ratan.maigen.data.response.ErrorResponse
import com.ratan.maigen.data.response.ListDestinationItem
import com.ratan.maigen.data.response.LoginResponse
import com.ratan.maigen.data.result.ResultState
import kotlinx.coroutines.flow.Flow

class DestinationRepository private constructor(private val apiServiceMobile: ApiServiceMobile, private val preferences: UserPreferences) {

    fun register(username: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiServiceMobile.register(username, email, password)
            val message = successResponse.message
            emit(ResultState.Success(message))
        } catch (e: HttpException) {
            val errorMessage: String
            if (e.code() == 400) {
                errorMessage = "Email telah digunakan"
                emit(ResultState.Error(errorMessage))
            } else {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                errorMessage = errorBody.message.toString()
                emit(ResultState.Error(errorMessage))
            }
        }
    }

    fun login(email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiServiceMobile.login(email, password)
            val data = successResponse.loginResult?.token
            emit(ResultState.Success(data))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(ResultState.Error(errorResponse.message!!))
        }
    }

    fun getSession(): Flow<UserModel> {
        return preferences.getSession()
    }

    suspend fun saveSession(user: UserModel) {
        preferences.saveSession(user)
    }

    suspend fun logout() {
        preferences.logout()
        instance = null
    }

    fun getSurveyPreference(): LiveData<Boolean> {
        return preferences.getSurveyPreference().asLiveData()
    }

    suspend fun saveSurveyPreference(preference: Boolean) {
        preferences.saveSurveyPreference(preference)
    }

    companion object {
        private const val TAG = "MainViewModel"
        @Volatile
        private var instance: DestinationRepository? = null
        fun getInstance(apiServiceMobile: ApiServiceMobile, pref: UserPreferences) =
            instance ?: synchronized(this) {
                instance ?: DestinationRepository(apiServiceMobile, pref)
            }.also { instance = it }
    }
}