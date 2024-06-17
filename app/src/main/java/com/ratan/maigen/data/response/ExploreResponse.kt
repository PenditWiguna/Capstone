package com.ratan.maigen.data.response

import com.google.gson.annotations.SerializedName

data class ExploreResponse(
    @SerializedName("results")
    val results: List<ExploreResult>? = null
)

data class ExploreResult(
    @SerializedName("Place_Id")
    val Place_Id: String? = null,

    @SerializedName("Place_Name")
    val Place_Name: String? = null,

    @SerializedName("Description")
    val Description: String? = null,

    @SerializedName("Weekend Holiday Price")
    val Weekend_Holiday_Price: String? = null,

    @SerializedName("Weekday Price")
    val Weekday_Price: String? = null,

    @SerializedName("Category")
    val Category: String? = null,

    @SerializedName("City")
    val City: String? = null,

    @SerializedName("Rating")
    val Rating: String? = null,

    @SerializedName("Alamat")
    val Alamat: String? = null,

    @SerializedName("Coordinate")
    val Coordinate: String? = null,

    @SerializedName("Lat")
    val Lat: String? = null,

    @SerializedName("Long")
    val Long: String? = null,

    @SerializedName("Gambar")
    val Gambar: String? = null
)
