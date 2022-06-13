package com.planradar.weatherapptask.domain.repository

import com.planradar.weatherapptask.data.local.entities.WeatherHistoryEntity
import com.planradar.weatherapptask.data.remote.dto.CityResponse
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.model.Weather
import com.planradar.weatherapptask.util.Result
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun searchCity(searchKey: String): Flow<Result<CityResponse, String>>

    suspend fun searchWeather(searchKey: String): Flow<Result<Weather, String>>


}