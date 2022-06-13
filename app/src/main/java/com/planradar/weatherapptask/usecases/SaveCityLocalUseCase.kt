package com.planradar.weatherapptask.usecases

import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.repository.CitiesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCityLocalUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository,
) {
    suspend operator fun invoke(city: City): Flow<Long> {
        return citiesRepository.saveCity(city)
    }
}