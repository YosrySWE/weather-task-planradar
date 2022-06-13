package com.planradar.weatherapptask.domain.usecases

import com.planradar.weatherapptask.data.remote.dto.CityResponse
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.repository.CitiesRepository
import com.planradar.weatherapptask.domain.repository.SearchRepository
import com.planradar.weatherapptask.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(
    private val searchRepository: SearchRepository) {
    suspend operator fun invoke(key: String): Flow<Result<CityResponse, String>> {
        return searchRepository.searchCity(key)
    }
}