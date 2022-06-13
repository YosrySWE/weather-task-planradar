package com.planradar.weatherapptask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.halanchallenge.di.qualifiers.ApplicationScope
import com.planradar.weatherapptask.data.local.dao.WeatherHistoryDao
import com.planradar.weatherapptask.data.local.entities.CityEntity
import com.planradar.weatherapptask.data.local.entities.WeatherHistoryEntity
import com.planradar.weatherapptask.data.local.dao.CitiesDao
import com.planradar.weatherapptask.util.Converters
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Database(
    entities = [CityEntity::class, WeatherHistoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
@Singleton
abstract class PlanRadarDatabase : RoomDatabase() {
    abstract fun citiesDao(): CitiesDao
    abstract fun historyDao(): WeatherHistoryDao

    class Callback @Inject constructor(
        private val database: Provider<PlanRadarDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}
