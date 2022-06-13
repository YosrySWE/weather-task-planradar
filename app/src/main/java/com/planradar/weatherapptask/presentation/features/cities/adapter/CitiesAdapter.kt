package com.planradar.weatherapptask.presentation.features.cities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.planradar.weatherapptask.databinding.RowCityBinding
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.model.fullName
import com.planradar.weatherapptask.util.extensions.disableTemp

class CitiesAdapter internal constructor(
    val callback: ItemClickListener? = null
) : ListAdapter<City, CitiesAdapter.ViewHolder>(CitiesCallback()) {

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(getItem(position))
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(var binding: RowCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: City) {
            binding.tvCityName.text = item.fullName()
            binding.content.setOnClickListener {
                callback?.onItemClick(item)
                it.disableTemp()
            }
            binding.ivInfo.setOnClickListener {
                callback?.onInfoClick(item.id!!, item.name)
                it.disableTemp()
            }
        }
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(item: City)
        fun onInfoClick(cityId: Long, cityName: String)
    }

    internal class CitiesCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }

    }

}