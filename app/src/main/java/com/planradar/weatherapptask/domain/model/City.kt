package com.planradar.weatherapptask.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val id: Long? = null,
    val name: String,
    val country: String,
) : Parcelable{

}

fun City.fullName(): String = "$name, $country"
