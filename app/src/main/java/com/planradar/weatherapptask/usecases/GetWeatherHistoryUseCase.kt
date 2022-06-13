package com.planradar.weatherapptask.usecases

import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.model.Weather
import com.planradar.weatherapptask.domain.repository.WeatherRepository
import com.planradar.weatherapptask.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherHistoryUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(cityId: Long): Flow<Result<List<Weather>, String>> {
        return weatherRepository.getWeatherHistory(cityId)
    }
}