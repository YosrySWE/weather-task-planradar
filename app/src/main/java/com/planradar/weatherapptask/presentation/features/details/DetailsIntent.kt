package com.planradar.weatherapptask.presentation.features.details

import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.model.Weather

sealed class DetailsIntent {
    data class InitFromCities(val city: City): DetailsIntent()
    data class SaveWeather(val weather: Weather): DetailsIntent()
    data class InitFromHistory(val weather: Weather): DetailsIntent()
}