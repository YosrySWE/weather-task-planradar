package com.planradar.weatherapptask.presentation.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.model.Weather
import com.planradar.weatherapptask.domain.usecases.GetWeatherUseCase
import com.planradar.weatherapptask.domain.usecases.SaveWeatherLocalUserCase
import com.planradar.weatherapptask.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val saveWeatherLocalUserCase: SaveWeatherLocalUserCase,
) : ViewModel() {

    val intentChannel = Channel<DetailsIntent>(Channel.UNLIMITED)

    private val job = Job()

    init {
        processIntent()
    }

    private val _state = MutableStateFlow<DetailsViewState>(DetailsViewState.Idle)
    val state: StateFlow<DetailsViewState> get() = _state

    private fun showLoading() {
        _state.value = DetailsViewState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = DetailsViewState.IsLoading(false)
    }


    private fun processIntent() {
        viewModelScope.launch(job) {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is DetailsIntent.InitFromCities -> {
                        getWeatherInfo(it.city)
                    }
                    is DetailsIntent.InitFromHistory -> {
                        _state.value = DetailsViewState.RenderDetails(it.weather)
                    }
                    is DetailsIntent.SaveWeather -> {
                        saveWeather(it.weather)
                    }

                }
            }
        }
    }

    private fun saveWeather(weather: Weather) {
        viewModelScope.launch(job) {
            saveWeatherLocalUserCase(weather).onStart { showLoading() }
                .catch {
                    _state.value = DetailsViewState.Error(it.message!!)
                }
                .onCompletion { hideLoading() }
                .collect {
                    if(it != (-1).toLong()){
                        _state.value = DetailsViewState.RenderDetails(weather)
                    }else{
                        _state.value = DetailsViewState.Error("Record Not Saved in Database")
                    }
                }
        }
    }

    private fun getWeatherInfo(city: City) {
        viewModelScope.launch(job) {
            getWeatherUseCase(city.name)
                .onStart { showLoading() }
                .catch {
                    _state.value = DetailsViewState.Error(it.message!!)
                }
                .onCompletion { hideLoading() }
                .collect {
                    when(it){
                        is Result.Success ->{
                            _state.value = DetailsViewState.Success(it.data!!)
                        }
                        is Result.Error ->{
                            _state.value = DetailsViewState.Error(it.message)
                        }
                    }
                }
        }
    }
    fun clear(){
        job.cancel()
    }
}