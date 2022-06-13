package com.planradar.weatherapptask.util.extensions

import kotlin.math.roundToInt

fun Double.fromKelvinToCelsius(): Double = ((this - 273.15) * 100).roundToInt() / 100.0