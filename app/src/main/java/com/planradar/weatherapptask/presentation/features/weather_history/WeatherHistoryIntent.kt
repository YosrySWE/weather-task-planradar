package com.planradar.weatherapptask.presentation.features.weather_history

import com.planradar.weatherapptask.domain.model.Weather

sealed class WeatherHistoryIntent {
    data class Init(val cityId: Long): WeatherHistoryIntent()
}