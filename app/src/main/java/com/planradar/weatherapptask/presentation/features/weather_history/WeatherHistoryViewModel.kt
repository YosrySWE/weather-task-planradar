package com.planradar.weatherapptask.presentation.features.weather_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planradar.weatherapptask.domain.usecases.GetWeatherHistoryUseCase
import com.planradar.weatherapptask.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHistoryViewModel @Inject constructor(
    private val getWeatherHistoryUseCase: GetWeatherHistoryUseCase,
) : ViewModel() {

    val intentChannel = Channel<WeatherHistoryIntent>(Channel.UNLIMITED)

    private val job = Job()

    init {
        processIntent()
    }

    private val _state = MutableStateFlow<WeatherHistoryViewState>(WeatherHistoryViewState.Idle)
    val state: StateFlow<WeatherHistoryViewState> get() = _state

    private fun showLoading() {
        _state.value = WeatherHistoryViewState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = WeatherHistoryViewState.IsLoading(false)
    }


    private fun processIntent() {
        viewModelScope.launch(job) {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is WeatherHistoryIntent.Init -> {
                        getWeatherList(it.cityId)
                    }


                }
            }
        }
    }

    private fun getWeatherList(cityId: Long) {
        viewModelScope.launch(job) {
            getWeatherHistoryUseCase(cityId)
                .onStart { showLoading() }
                .catch {
                    _state.value = WeatherHistoryViewState.Error(it.message!!)
                }
                .onCompletion { hideLoading() }
                .collect {
                    when (it) {
                        is Result.Success -> {
                            if (it.data.isNullOrEmpty()) {
                                _state.value = WeatherHistoryViewState.Empty
                            } else {
                                _state.value =
                                    WeatherHistoryViewState.Success(it.data.toMutableList())
                            }
                        }
                        is Result.Error -> {
                            _state.value = WeatherHistoryViewState.Error(it.message)
                        }
                    }
                }
        }
    }

    fun clear() {
        job.cancel()
    }
}