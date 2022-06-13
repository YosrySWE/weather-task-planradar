package com.planradar.weatherapptask.usecases

import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.repository.CitiesRepository
import com.planradar.weatherapptask.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedCitiesUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    suspend operator fun invoke(): Flow<Result<List<City>, String>> {
        return citiesRepository.cities()
    }
}