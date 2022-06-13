package com.planradar.weatherapptask.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CityResponse (

    @SerializedName("coord")
    @Expose
    val coord: Coordinates,
    @SerializedName("weather")
    @Expose
    val weather: List<WeatherInfo>? = null,
    @SerializedName("base")
    @Expose
    val base: String,
    @SerializedName("main")
    @Expose
    val main:Main,
    @SerializedName("visibility")
    @Expose
    val visibility:Int,
    @SerializedName("wind")
    @Expose
    val wind: Wind,
    @SerializedName("clouds")
    @Expose
    val clouds: Clouds,
    @SerializedName("dt")
    @Expose
    val dt: Int,
    @SerializedName("sys")
    @Expose
    val sys: Sys,
    @SerializedName("timezone")
    @Expose
    val timezone: Int,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("cod")
    @Expose
    val cod: Int
)

data class Clouds (
    @SerializedName("all")
    @Expose
    val all: Int
)

data class Coordinates (
    @SerializedName("lon")
    @Expose
    val lon: Double,
    @SerializedName("lat")
    @Expose
    val lat: Double
)


data class Main (

    @SerializedName("temp")
    @Expose
    val temp : Double,
    @SerializedName("feels_like")
    @Expose
    val feelsLike: Double,
    @SerializedName("temp_min")
    @Expose
    val tempMin: Double,
    @SerializedName("temp_max")
    @Expose
    val tempMax: Double,
    @SerializedName("pressure")
    @Expose
    val pressure: Int,
    @SerializedName("humidity")
    @Expose
    val humidity: Double

)


data class Sys (

    @SerializedName("type")
    @Expose
    val typeval : Int,
    @SerializedName("id")
    @Expose
    val idval : Int,
    @SerializedName("country")
    @Expose
    val country: String,
    @SerializedName("sunrise")
    @Expose
    val sunrise:Int,
    @SerializedName("sunset")
    @Expose
    val sunset: Int
)

data class WeatherInfo (

    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("main")
    @Expose
    val main: String,
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("icon")
    @Expose
    val icon: String

    )

data class Wind (

    @SerializedName("speed")
    @Expose
    val speed: Double,
    @SerializedName("deg")
    @Expose
    val deg: Int
)