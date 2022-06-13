package com.planradar.weatherapptask.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val id: Long?,
    val cityId: Long,
    val cityName: String,
    val countryName: String,
    val date: Long,
    val description: String,
    val temp: Double,
    val humidity: Double,
    val windSpeed: Double,
    val iconId: String
) : Parcelable