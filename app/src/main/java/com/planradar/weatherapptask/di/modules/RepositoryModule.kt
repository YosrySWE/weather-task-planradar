package com.planradar.weatherapptask.di.modules

import com.planradar.weatherapptask.data.repository.CitiesRepositoryImpl
import com.planradar.weatherapptask.data.repository.SearchRepositoryImpl
import com.planradar.weatherapptask.data.repository.WeatherRepositoryImpl
import com.planradar.weatherapptask.domain.repository.CitiesRepository
import com.planradar.weatherapptask.domain.repository.SearchRepository
import com.planradar.weatherapptask.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideCitiesRepository(repository: CitiesRepositoryImpl): CitiesRepository {
        return repository
    }

    @Provides
    @Singleton
    fun provideWeatherHistoryRepository(repository: WeatherRepositoryImpl): WeatherRepository {
        return repository
    }

    @Provides
    @Singleton
    fun provideSearchRepository(repository: SearchRepositoryImpl): SearchRepository {
        return repository
    }

}