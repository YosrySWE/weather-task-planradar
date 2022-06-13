package com.planradar.weatherapptask.usecases

import com.planradar.weatherapptask.domain.repository.CitiesRepository
import com.planradar.weatherapptask.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsCityExistUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    suspend operator fun invoke(cityName: String): Boolean {
        return citiesRepository.isCityExist(cityName)
    }
}