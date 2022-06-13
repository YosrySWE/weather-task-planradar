package com.planradar.weatherapptask.presentation.features.details

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.planradar.weatherapptask.R
import com.planradar.weatherapptask.databinding.FragmentDetailsBinding
import com.planradar.weatherapptask.databinding.FragmentWeatherHistoryBinding
import com.planradar.weatherapptask.presentation.platform.BaseFragment
import com.planradar.weatherapptask.util.extensions.*
import com.planradar.weatherapptask.util.formatFetchDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(){
    override val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun render() {
        binding.header.backBtn.show()
        binding.header.tvTitle.text = ""
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.state.collect {
                    when(it){
                        is DetailsViewState.Idle ->{
                            binding.header.tvTitle.text = ""
                            binding.header.backBtn.setOnClickListener { view ->
                                findNavController().navigateUp()
                                view.disableTemp()
                            }
                            if(args.city != null){
                                viewModel.intentChannel.trySend(DetailsIntent.InitFromCities(args.city!!))
                            }else{
                                viewModel.intentChannel.trySend(DetailsIntent.InitFromHistory(args.weather!!))
                            }
                        }
                        is DetailsViewState.IsLoading ->{
                            loading(it.isLoading)
                        }
                        is DetailsViewState.Success ->{
                            loading(false)
                            viewModel.intentChannel.trySend(DetailsIntent.SaveWeather(it.weather))
                        }
                        is DetailsViewState.Error ->{
                            loading(false)
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        is DetailsViewState.RenderDetails ->{
                            loading(false)
                            it.weather.apply {
                                binding.ivWeather.loadImage(buildWeatherIconUrl(iconId))
                                binding.tvWeatherDate.text = if (cityName.isNotEmpty())
                                    getString(R.string.txt_weather_date, cityName, formatFetchDate(date)) else ""
                                binding.tvTemValue.text = "$temp C"
                                binding.tvWindValue.text = "$windSpeed kmh"
                                binding.tvHumidityValue.text = "$humidity %"
                                binding.tvDescriptionValue.text = description
                                binding.tvCityName.text = "$cityName, $countryName"
                            }
                        }
                    }
                }
            }
            repeatOnLifecycle(Lifecycle.State.DESTROYED){
                viewModel.clear()
            }
        }
    }
    private fun loading(flag: Boolean){
        if (flag) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }


    override fun online() {
        // update UI
    }

    override fun offline() {
        // update UI
    }
}