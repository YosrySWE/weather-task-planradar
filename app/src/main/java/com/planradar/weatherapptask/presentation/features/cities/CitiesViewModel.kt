package com.planradar.weatherapptask.presentation.features.cities

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planradar.weatherapptask.domain.usecases.GetSavedCitiesUseCase
import com.planradar.weatherapptask.util.Result.Error
import com.planradar.weatherapptask.util.Result.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class CitiesViewModel @Inject constructor(
    private val getSavedCitiesUseCase: GetSavedCitiesUseCase,
) : ViewModel() {

    val intentChannel = Channel<CitiesIntent>(Channel.UNLIMITED)

    private val job = Job()

    init {
        processIntent()
    }

    private val _state = MutableStateFlow<CitiesViewState>(CitiesViewState.Init)
    val state: StateFlow<CitiesViewState> get() = _state

    private fun showLoading() {
        _state.value = CitiesViewState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = CitiesViewState.IsLoading(false)
    }


    private fun processIntent() {
        viewModelScope.launch(job) {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is CitiesIntent.Idle -> {
                        _state.value = CitiesViewState.Init
                    }
                    is CitiesIntent.LoadCities -> {
                        loadCities()
                    }

                    is CitiesIntent.ItemClicked -> {
                        _state.value = CitiesViewState.NavToHistoryScreen(it.city)
                    }

                }
            }
        }
    }

    private fun loadCities() {

        viewModelScope.launch(job) {
            getSavedCitiesUseCase()
                .onStart { showLoading() }
                .catch {
                    Log.e("TAG", it.message!!)
                }

                .onCompletion { hideLoading() }
                .collect {
                    when (it) {
                        is Success -> {
                            _state.value =
                                CitiesViewState.Success(it.data!!.toMutableList())
                        }
                        is Error -> {
                            _state.value = CitiesViewState.Error(it.message)
                        }
                    }
                }
        }
    }

    fun clear() {
        job.cancel()
    }
}