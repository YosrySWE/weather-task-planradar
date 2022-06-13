package com.planradar.weatherapptask.data.local.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "weather_history"
)
data class WeatherHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "city_id", index = true) val cityId: Long,
    @ColumnInfo(name = "city_name") val cityName: String,
    @ColumnInfo(name = "country_name") val countryName: String,
    val date: Long,
    val description: String,
    val temp: Double,
    val humidity: Double,
    @ColumnInfo(name = "wind_speed") val windSpeed: Double,
    @ColumnInfo(name = "icon_id") val iconId: String
)