package com.planradar.weatherapptask.data.repository

import com.planradar.weatherapptask.data.local.dao.CitiesDao
import com.planradar.weatherapptask.data.mapper.toEntity
import com.planradar.weatherapptask.data.mapper.toModel
import com.planradar.weatherapptask.data.remote.api.CityApi
import com.planradar.weatherapptask.data.remote.dto.CityResponse
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.repository.CitiesRepository
import com.planradar.weatherapptask.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val citiesDao: CitiesDao,
) : CitiesRepository {
    override suspend fun saveCity(city: City): Flow<Long> {
        return flow {
            emit(citiesDao.saveCity(city = city.toEntity()))
        }
    }

    override suspend fun cities(): Flow<Result<List<City>, String>> {
        return flow {
            citiesDao.cities().collect {
                try {
                    emit(Result.Success(it.map { item -> item.toModel() }))
                } catch (e: Exception) {
                    emit(Result.Error(e.message.toString()))
                }
            }
        }
    }


    override suspend fun isCityExist(cityName: String): Boolean {
        return withContext(Dispatchers.IO) {
            citiesDao.getRowCount(cityName) > 0
        }
    }


}