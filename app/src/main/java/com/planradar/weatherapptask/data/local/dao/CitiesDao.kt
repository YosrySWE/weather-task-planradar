package com.planradar.weatherapptask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.planradar.weatherapptask.data.local.entities.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {
    @Query("SELECT * FROM cities")
    fun cities(): Flow<MutableList<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = CityEntity::class)
    suspend fun saveCity(city: CityEntity): Long

    @Query("SELECT COUNT('id') FROM cities WHERE city_name = :name")
    fun getRowCount(name: String): Int
}