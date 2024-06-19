package com.ratan.maigen.data.response

import com.google.gson.annotations.SerializedName

data class DestinationDetailResponse(
    @SerializedName("place_detail")
    val placeDetail: PlaceDetail
)

data class PlaceDetail(
    @SerializedName("Alamat")
    val address: String,

    @SerializedName("Category")
    val category: String,

    @SerializedName("City")
    val city: String,

    @SerializedName("Description")
    val description: String,

    @SerializedName("Gambar")
    val image: String,

    @SerializedName("Place_Name")
    val placeName: String,

    @SerializedName("Rating")
    val rating: Float,

    @SerializedName("Weekday_Price")
    val weekdayPrice: Int,

    @SerializedName("Weekend_Holiday_Price")
    val weekendHolidayPrice: Int
)