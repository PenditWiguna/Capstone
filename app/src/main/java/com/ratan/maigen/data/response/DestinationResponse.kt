package com.ratan.maigen.data.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class DestinationResponse (
    @field:SerializedName("listDestination")
    val listDestination: List<ListDestinationItem>,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class ListDestinationItem(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("lon")
    val lon: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(photoUrl)
        parcel.writeString(createdAt)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeValue(lon)
        parcel.writeValue(lat)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListDestinationItem> {
        override fun createFromParcel(parcel: Parcel): ListDestinationItem {
            return ListDestinationItem(parcel)
        }

        override fun newArray(size: Int): Array<ListDestinationItem?> {
            return arrayOfNulls(size)
        }
    }
}