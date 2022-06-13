package com.planradar.weatherapptask.presentation.features.weather_history

import com.planradar.weatherapptask.domain.model.Weather
import com.planradar.weatherapptask.presentation.features.search.SearchViewState

sealed class WeatherHistoryViewState {
    data class IsLoading(val isLoading: Boolean) : WeatherHistoryViewState()
    data class Success(val list: MutableList<Weather>): WeatherHistoryViewState()
    data class Error(val message: String): WeatherHistoryViewState()
    object Empty: WeatherHistoryViewState()
    object Idle: WeatherHistoryViewState()

}