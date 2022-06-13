package com.planradar.weatherapptask.domain.usecases

import com.planradar.weatherapptask.domain.repository.CitiesRepository
import com.planradar.weatherapptask.domain.repository.WeatherRepository
import javax.inject.Inject

class IsWeatherExistUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(cityId: Long): Boolean {
        return weatherRepository.isWeatherExist(cityId)
    }
}