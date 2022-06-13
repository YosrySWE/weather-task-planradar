package com.planradar.weatherapptask.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlanRadarApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}