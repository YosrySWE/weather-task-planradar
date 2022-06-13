package com.planradar.weatherapptask.presentation.features.cities

import com.planradar.weatherapptask.domain.model.City

sealed class CitiesViewState {
    object Init: CitiesViewState()
    data class IsLoading(val isLoading: Boolean) : CitiesViewState()
    data class NavToDetailsScreen(val city: City): CitiesViewState()
    data class NavToHistoryScreen(val city: City): CitiesViewState()
    data class Success(val response: MutableList<City>): CitiesViewState()
    data class Error(val message: String) : CitiesViewState()
}