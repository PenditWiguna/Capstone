package com.ratan.maigen.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)