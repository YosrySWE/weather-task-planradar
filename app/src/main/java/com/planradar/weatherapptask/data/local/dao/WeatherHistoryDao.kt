package com.planradar.weatherapptask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.planradar.weatherapptask.data.local.entities.WeatherHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherHistoryDao {
    @Query("SELECT * FROM weather_history WHERE city_id = :cityId")
    fun getCityHistory(cityId: Long): Flow<List<WeatherHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = WeatherHistoryEntity::class)
    suspend fun saveCity(city: WeatherHistoryEntity): Long

    @Query("SELECT COUNT('id') FROM weather_history WHERE city_id = :id")
    fun getRowCount(id: Long): Int
}