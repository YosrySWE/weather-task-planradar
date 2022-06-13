package com.planradar.weatherapptask.data.remote.api

import com.planradar.weatherapptask.data.remote.dto.CityResponse
import com.planradar.weatherapptask.util.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApi {
    @GET("weather")
    suspend fun getCity(
        @Query("q") cityName: String,
        @Query("appid") apiId: String = API_KEY
    ): Response<CityResponse>
}