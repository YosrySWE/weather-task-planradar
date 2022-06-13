package com.planradar.weatherapptask.util.extensions

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.planradar.weatherapptask.R

fun ImageView.loadImage(url: String){
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    Glide.with(this.context).load(url).placeholder(circularProgressDrawable).error(R.drawable.ic_cloud).into(this)
}

fun buildWeatherIconUrl(iconId: String): String {
    return "https://openweathermap.org/img/w/$iconId.png"
}