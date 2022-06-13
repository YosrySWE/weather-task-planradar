package com.planradar.weatherapptask.presentation.features.weather_history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.planradar.weatherapptask.databinding.RowCityBinding
import com.planradar.weatherapptask.databinding.RowHistoryBinding
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.model.Weather
import com.planradar.weatherapptask.domain.model.fullName
import com.planradar.weatherapptask.util.extensions.buildWeatherIconUrl
import com.planradar.weatherapptask.util.extensions.loadImage
import com.planradar.weatherapptask.util.formatFetchDate

class HistoryAdapter internal constructor(
    val callback: ItemClickListener? = null
) : ListAdapter<Weather, HistoryAdapter.ViewHolder>(HistoryCallback()) {

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position))
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(var binding: RowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Weather) {
            binding.weatherIcon.loadImage(buildWeatherIconUrl(item.iconId))
            binding.txtWeatherDate.text = formatFetchDate(item.date)
            binding.txtDescription.text = "${item.description}, ${item.temp} C"
            binding.root.setOnClickListener {
                callback?.onItemClick(item)
            }
        }
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(item: Weather)
    }

    internal class HistoryCallback : DiffUtil.ItemCallback<Weather>() {
        override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
            return oldItem == newItem
        }

    }

}