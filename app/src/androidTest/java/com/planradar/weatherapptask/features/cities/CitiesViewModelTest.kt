package com.planradar.weatherapptask.features.cities

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.planradar.weatherapptask.data.local.PlanRadarDatabase
import com.planradar.weatherapptask.data.repository.CitiesRepositoryImpl
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.repository.CitiesRepository
import com.planradar.weatherapptask.presentation.features.cities.CitiesIntent
import com.planradar.weatherapptask.presentation.features.cities.CitiesViewModel
import com.planradar.weatherapptask.presentation.features.cities.CitiesViewState
import com.planradar.weatherapptask.usecases.GetSavedCitiesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CitiesViewModelTest {
    private lateinit var viewModel: CitiesViewModel

    lateinit var getSavedCitiesUseCase: GetSavedCitiesUseCase
    lateinit var citiesRepository: CitiesRepository

    //    Mockito.mock(GetSavedCitiesUseCase::class.java)
    private lateinit var db: PlanRadarDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PlanRadarDatabase::class.java
        ).build()
        viewModel = CitiesViewModel(getSavedCitiesUseCase)
        citiesRepository = CitiesRepositoryImpl(db.citiesDao())
        getSavedCitiesUseCase = GetSavedCitiesUseCase(citiesRepository)
    }

    @Test
    fun whenSavedCitiesReturned_CheckOnUiState() = runBlocking {
//        val fakeData = provideFakeCities()

//        Mockito.`when`(getSavedCitiesUseCase).thenAnswer {
//            flowOf(Result.Success(fakeData))
//        }

//        val list = viewModel.getSavedCitiesUseCase().first()

        viewModel.intentChannel.trySend(CitiesIntent.LoadCities)

        assertThat((viewModel.state.value)).isInstanceOf(CitiesViewState.Success::class.java)
//        assertThat((list as Result.Success).data?.size).isEqualTo(2)


    }

    fun provideFakeCities() = listOf(
        City(1, "Cairo", "EG"),
        City(2, "London", "GB")
    )
}
