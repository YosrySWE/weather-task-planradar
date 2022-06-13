package com.planradar.weatherapptask.domain.repository

import com.planradar.weatherapptask.data.remote.dto.CityResponse
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.util.Result
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    suspend fun saveCity(city: City): Flow<Long>

    suspend fun cities(): Flow<Result<List<City>, String>>

    suspend fun isCityExist(cityName: String):Boolean


}