package com.planradar.weatherapptask.domain.usecases

import com.planradar.weatherapptask.domain.model.Weather
import com.planradar.weatherapptask.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveWeatherLocalUserCase  @Inject constructor(
    private val weatherHistoryRepository: WeatherRepository,
) {
    suspend operator fun invoke(weather: Weather): Flow<Long> {
        return weatherHistoryRepository.saveWeather(weather)
    }
}