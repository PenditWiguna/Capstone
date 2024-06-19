package com.ratan.maigen.data.api

import com.ratan.maigen.BuildConfig
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
        fun getApiServiceMobile(): ApiServiceMobile {
            return createApiService(BuildConfig.URL_MD, ApiServiceMobile::class.java)
        }

        fun getApiServiceModel(): ApiServiceModel {
            return createApiService(BuildConfig.URL_ML, ApiServiceModel::class.java)
        }

        private fun <T> createApiService(baseUrl: String, apiServiceClass: Class<T>): T {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(apiServiceClass)
        }
}