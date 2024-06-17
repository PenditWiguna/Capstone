package com.ratan.maigen.data.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @field:SerializedName("message")
    val message: String
)