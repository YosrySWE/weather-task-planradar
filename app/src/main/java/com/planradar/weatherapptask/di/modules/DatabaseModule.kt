package com.planradar.weatherapptask.di.modules

import android.app.Application
import androidx.room.Room
import com.example.halanchallenge.di.qualifiers.ApplicationScope
import com.planradar.weatherapptask.data.local.PlanRadarDatabase
import com.planradar.weatherapptask.data.local.dao.WeatherHistoryDao
import com.planradar.weatherapptask.data.local.dao.CitiesDao
import com.planradar.weatherapptask.util.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
        callback: PlanRadarDatabase.Callback
    ): PlanRadarDatabase {
        return Room.databaseBuilder(application, PlanRadarDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    @Provides
    fun provideCitiesDao(db: PlanRadarDatabase): CitiesDao {
        return db.citiesDao()
    }


    @Provides
    fun provideWeatherHistoryDao(db: PlanRadarDatabase): WeatherHistoryDao {
        return db.historyDao()
    }
}