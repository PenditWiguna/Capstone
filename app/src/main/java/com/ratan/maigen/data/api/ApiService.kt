package com.ratan.maigen.data.api

import com.ratan.maigen.data.response.DestinationResponse
import com.ratan.maigen.data.response.LoginResponse
import com.ratan.maigen.data.response.RegisterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("destination")
    suspend fun getDestination(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): DestinationResponse
}