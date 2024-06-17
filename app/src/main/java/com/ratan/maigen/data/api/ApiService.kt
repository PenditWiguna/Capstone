package com.ratan.maigen.data.api

import com.ratan.maigen.data.response.DeleteProfileResponse
import com.ratan.maigen.data.response.DestinationResponse
import com.ratan.maigen.data.response.ExploreResponse
import com.ratan.maigen.data.response.LoginResponse
import com.ratan.maigen.data.response.LogoutResponse
import com.ratan.maigen.data.response.ProfileResponse
import com.ratan.maigen.data.response.RegisterResponse
import com.ratan.maigen.data.response.UpdateProfileResponse
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("/auth/signup")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("/auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @POST("/auth/logout")
    suspend fun logout(
        @Header("Authorization") token: String
    ): LogoutResponse

    @GET("destination")
    suspend fun getDestination(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): DestinationResponse

    @GET("/user/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): ProfileResponse

    @FormUrlEncoded
    @PUT("/user/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Field("new_username") newUsername: String?,
        @Field("new_email") newEmail: String?,
        @Field("new_password") newPassword: String?
    ): UpdateProfileResponse

    @DELETE("/user/profile")
    suspend fun deleteProfile(
        @Header("Authorization") token: String
    ): DeleteProfileResponse

    @GET("/search")
    suspend fun explore(
        @Header("Authorization") token: String,
        @Query("placeName") placeName: String
    ): ExploreResponse
}