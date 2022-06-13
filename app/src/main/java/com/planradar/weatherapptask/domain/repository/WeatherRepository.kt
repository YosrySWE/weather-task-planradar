package com.planradar.weatherapptask.domain.repository

import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.model.Weather
import com.planradar.weatherapptask.util.Result
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun saveWeather(weather: Weather): Flow<Long>

    suspend fun getWeatherHistory(cityId: Long): Flow<Result<List<Weather>, String>>


    suspend fun isWeatherExist(cityId: Long): Boolean


}