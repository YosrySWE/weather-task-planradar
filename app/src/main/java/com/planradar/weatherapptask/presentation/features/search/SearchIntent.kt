package com.planradar.weatherapptask.presentation.features.search

import com.planradar.weatherapptask.data.remote.dto.CityResponse
import com.planradar.weatherapptask.domain.model.City

sealed class SearchIntent {
    data class SearchCity(val key: String): SearchIntent()
    data class SaveCity(val city: CityResponse): SearchIntent()
}