package com.planradar.weatherapptask.data.repository

import com.planradar.weatherapptask.data.remote.api.CityApi
import com.planradar.weatherapptask.data.remote.dto.CityResponse
import com.planradar.weatherapptask.domain.model.Weather
import com.planradar.weatherapptask.domain.repository.SearchRepository
import com.planradar.weatherapptask.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: CityApi,

    ) : SearchRepository {
    override suspend fun searchCity(searchKey: String): Flow<Result<CityResponse, String>> {

        return flow {
            val connection = api.getCity(searchKey)
            if (connection.isSuccessful) {

                val response = connection.body() as CityResponse

                if (response.name.isNotEmpty()) {

                    emit(
                        Result.Success(
                            response
                        )
                    )
                } else {
                    emit(Result.Error("No Record"))
                }
            } else {
                when {
                    connection.code() == 400 -> {
                        // Bad Request
                        emit(Result.Error("Bad Request"))
                    }
                    connection.code() == 404 -> {
                        emit(Result.Error("No Result"))
                    }
                    else -> {
                        emit(Result.Error(connection.message()))
                    }
                }
            }
        }
    }

    override suspend fun searchWeather(searchKey: String): Flow<Result<Weather, String>> {
        return flow {
            val connection = api.getCity(searchKey)
            if (connection.isSuccessful) {

                val response = connection.body() as CityResponse

                if (response.name.isNotEmpty()) {

                    emit(
                        Result.Success(
                            Weather(
                                Random().nextLong(),
                                cityId = response.id.toLong(),
                                cityName = response.name,
                                countryName = response.sys.country,
                                date = Date().time,
                                description = response.weather!![0].description,
                                iconId = response.weather[0].icon,
                                temp = response.main.temp,// Convert it to celsius
                                humidity = response.main.humidity,
                                windSpeed = response.wind.speed
                            )
                        )
                    )
                } else {
                    emit(Result.Error("No Record"))
                }
            } else {
                when {
                    connection.code() == 400 -> {
                        // Bad Request
                        emit(Result.Error("Bad Request"))
                    }
                    connection.code() == 401 -> {
                        emit(Result.Error("UNAUTHORIZED: Token expired"))
                    }
                    connection.code() == 404 -> {
                        emit(Result.Error("No Result"))
                    }
                    else -> {
                        emit(Result.Error(connection.message()))
                    }
                }
            }
        }
    }
}