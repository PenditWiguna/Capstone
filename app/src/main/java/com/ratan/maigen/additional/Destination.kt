package com.ratan.maigen.additional

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Destination (
    val name: String,
    val description: String,
    val photo: String
) : Parcelable