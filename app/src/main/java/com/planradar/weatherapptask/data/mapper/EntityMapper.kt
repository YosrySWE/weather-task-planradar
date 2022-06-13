package com.planradar.weatherapptask.data.mapper

import com.planradar.weatherapptask.data.local.entities.CityEntity
import com.planradar.weatherapptask.data.local.entities.WeatherHistoryEntity
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.model.Weather

fun City.toEntity() = CityEntity(
    this.id, this.name, this.country
)

fun CityEntity.toModel() = City(
    this.id, this.name, this.country
)

fun Weather.toEntity() = WeatherHistoryEntity(
    this.id,
    this.cityId,
    this.cityName,
    this.countryName,
    this.date,
    this.description,
    this.temp,
    this.humidity,
    this.windSpeed,
    this.iconId
)

fun WeatherHistoryEntity.toModel() = Weather(
    this.id,
    this.cityId,
    this.cityName,
    this.countryName,
    this.date,
    this.description,
    this.temp,
    this.humidity,
    this.windSpeed,
    this.iconId
)