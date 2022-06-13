package com.planradar.weatherapptask

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.planradar.weatherapptask.data.local.PlanRadarDatabase
import com.planradar.weatherapptask.data.local.dao.CitiesDao
import com.planradar.weatherapptask.data.local.dao.WeatherHistoryDao
import com.planradar.weatherapptask.data.mapper.toEntity
import com.planradar.weatherapptask.data.repository.CitiesRepositoryImpl
import com.planradar.weatherapptask.domain.model.City
import com.google.common.truth.Truth.assertThat
import com.planradar.weatherapptask.domain.model.Weather

import com.planradar.weatherapptask.usecases.GetSavedCitiesUseCase
import com.planradar.weatherapptask.usecases.SaveCityLocalUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) // Annotate with @RunWith
class PlanRadarDatabaseTest {

    // get reference to the PlanRadarDatabase, WeatherDao and CitiesDao classes
    private lateinit var db: PlanRadarDatabase
    private lateinit var citiesDao: CitiesDao
    private lateinit var historyDao: WeatherHistoryDao

    @Before
    fun setUp(){
        db =  Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PlanRadarDatabase::class.java).build()
        citiesDao = db.citiesDao()
        historyDao = db.historyDao()
    }
    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun saveCity() = runBlocking {
        citiesDao.saveCity(City(1,"Cairo", "Eg").toEntity())

        val list = citiesDao.cities().first()

        assertThat(list).isNotEmpty()
        assertThat(list[0].id).isEqualTo(1)
    }

    @Test
    fun saveWeather()= runBlocking{
        historyDao.saveCity(
            Weather(1,1,"Cairo", "Eg", 36600, "Good City", 22.5,44.toDouble(),20.toDouble(),"01d")
                .toEntity()
        )
        val list = historyDao.getCityHistory(1).first()
        assertThat(list).isNotEmpty()
        assertThat(list[0].id).isEqualTo(1)
    }
}