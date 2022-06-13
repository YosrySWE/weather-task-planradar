package com.planradar.weatherapptask.data.repository

import com.planradar.weatherapptask.data.local.dao.WeatherHistoryDao
import com.planradar.weatherapptask.data.mapper.toEntity
import com.planradar.weatherapptask.data.mapper.toModel
import com.planradar.weatherapptask.domain.model.Weather
import com.planradar.weatherapptask.domain.repository.WeatherRepository
import com.planradar.weatherapptask.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext


class WeatherRepositoryImpl @Inject constructor(
    private val weatherHistoryDao: WeatherHistoryDao,
) : WeatherRepository {
    override suspend fun saveWeather(weather: Weather): Flow<Long> {
        return flow { emit(weatherHistoryDao.saveCity(weather.toEntity())) }
    }

    override suspend fun getWeatherHistory(cityId: Long): Flow<Result<List<Weather>, String>> {
        return flow {
            weatherHistoryDao.getCityHistory(cityId).collect {
                try {
                    emit(Result.Success(it.map { item -> item.toModel() }))
                } catch (e: Exception) {
                    emit(Result.Error(e.message.toString()))
                }
            }
        }
    }

    override suspend fun isWeatherExist(cityId: Long): Boolean {
        return withContext(Dispatchers.IO) {
            weatherHistoryDao.getRowCount(cityId) > 0
        }
    }
}