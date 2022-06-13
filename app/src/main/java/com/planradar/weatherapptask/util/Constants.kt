package com.planradar.weatherapptask.util

import android.widget.TextView
import com.planradar.weatherapptask.R
import java.text.SimpleDateFormat
import java.util.*

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
const val DATABASE_NAME = "plan_radar.db"
const val API_KEY = "f5cb0b965ea1564c50c6f1b74534d823"

fun formatFetchDate(date: Long): String {
    val format = SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale.getDefault())
    return format.format(date)
}