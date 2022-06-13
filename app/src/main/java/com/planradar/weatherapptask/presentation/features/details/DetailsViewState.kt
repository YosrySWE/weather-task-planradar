package com.planradar.weatherapptask.presentation.features.details

import com.planradar.weatherapptask.data.remote.dto.CityResponse
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.model.Weather
import com.planradar.weatherapptask.presentation.features.cities.CitiesViewState

sealed class DetailsViewState {
    data class IsLoading(val isLoading: Boolean) : DetailsViewState()
    data class Success(val weather: Weather): DetailsViewState()
    data class Error(val message: String) : DetailsViewState()
    data class RenderDetails(val weather: Weather): DetailsViewState()
    object Idle: DetailsViewState()
}