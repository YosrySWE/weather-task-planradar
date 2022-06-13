package com.planradar.weatherapptask.presentation.features.cities

import com.planradar.weatherapptask.domain.model.City

sealed class CitiesIntent {
    data class ItemClicked(val city: City) : CitiesIntent()
    object LoadCities: CitiesIntent()
    object Idle: CitiesIntent()
}