package com.ratan.maigen.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ratan.maigen.data.di.Injection
import com.ratan.maigen.data.repository.DestinationRepository
import com.ratan.maigen.data.repository.RecommendationRepository

class ViewModelFactory(private val destinationRepository: DestinationRepository, private val recommendationRepository: RecommendationRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(destinationRepository, recommendationRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideRepositoryMobile(context),
                    Injection.provideRepositoryModel(context)
                ).also { INSTANCE = it }
            }

            return INSTANCE as ViewModelFactory
        }

//        @JvmStatic
//        fun getInstance(context: Context): ViewModelFactory {
//            if (INSTANCE == null) {
//                synchronized(ViewModelFactory::class.java) {
//                    INSTANCE = ViewModelFactory(
//                        Injection.provideRepositoryMobiile(context)
//                    )
//                }
//            }
//            return INSTANCE as ViewModelFactory
//        }
    }
}