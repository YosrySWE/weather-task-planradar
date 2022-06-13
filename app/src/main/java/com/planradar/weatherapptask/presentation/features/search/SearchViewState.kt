package com.planradar.weatherapptask.presentation.features.search

import com.planradar.weatherapptask.data.remote.dto.CityResponse
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.presentation.features.cities.CitiesViewState

sealed class SearchViewState {
    object Init: SearchViewState()
    data class IsLoading(val isLoading: Boolean) : SearchViewState()
    data class NavToCities(val city: CityResponse): SearchViewState()
    data class Success(val response: CityResponse): SearchViewState()
    data class Error(val message: String) : SearchViewState()
}