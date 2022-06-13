package com.planradar.weatherapptask.domain.usecases

import com.planradar.weatherapptask.domain.model.Weather
import com.planradar.weatherapptask.domain.repository.SearchRepository
import com.planradar.weatherapptask.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(cityName: String): Flow<Result<Weather, String>> {
        return searchRepository.searchWeather(cityName)
    }
}