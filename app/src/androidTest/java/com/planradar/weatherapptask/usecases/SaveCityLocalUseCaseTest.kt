package com.planradar.weatherapptask.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest

import org.junit.After
import org.junit.Before
import org.junit.Test
import com.planradar.weatherapptask.data.local.PlanRadarDatabase
import com.planradar.weatherapptask.data.repository.CitiesRepositoryImpl
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.repository.CitiesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class SaveCityLocalUseCaseTest {
    lateinit var database: PlanRadarDatabase
    lateinit var getSavedCitiesUseCase: GetSavedCitiesUseCase
    lateinit var saveCityLocalUseCase: SaveCityLocalUseCase
    lateinit var citiesRepository : CitiesRepository

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @Before
    fun setUp() {
        database =  Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PlanRadarDatabase::class.java).build()
        citiesRepository = CitiesRepositoryImpl(database.citiesDao())
        getSavedCitiesUseCase = GetSavedCitiesUseCase(citiesRepository)
        saveCityLocalUseCase = SaveCityLocalUseCase(citiesRepository)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun fakeData() = runBlocking{
        val list = mutableListOf<City>()
        list.add(City(1, "cairo", "eg"))
        list.add(City(2, "cairo", "eg"))
        list.add(City(3, "cairo", "eg"))
        list.add(City(4, "cairo", "eg"))
        list.add(City(5, "cairo", "eg"))

        list.forEach{
            saveCityLocalUseCase(it)

        }
    }
}