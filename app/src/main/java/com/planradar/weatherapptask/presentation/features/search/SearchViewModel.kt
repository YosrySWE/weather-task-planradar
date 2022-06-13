package com.planradar.weatherapptask.presentation.features.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.planradar.weatherapptask.data.remote.dto.CityResponse
import com.planradar.weatherapptask.domain.model.City
import com.planradar.weatherapptask.domain.usecases.SaveCityLocalUseCase
import com.planradar.weatherapptask.domain.usecases.SearchCityUseCase
import com.planradar.weatherapptask.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class SearchViewModel @Inject constructor(
    private val searchCityUseCase: SearchCityUseCase,
    private val saveCityLocalUseCase: SaveCityLocalUseCase
) : ViewModel() {

    val intentChannel = Channel<SearchIntent>(Channel.UNLIMITED)

    private val job = Job()

    init {
        processIntent()
    }

    private val _state = MutableStateFlow<SearchViewState>(SearchViewState.Init)
    val state: StateFlow<SearchViewState> get() = _state

    private fun showLoading() {
        _state.value = SearchViewState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = SearchViewState.IsLoading(false)
    }


    private fun processIntent() {
        viewModelScope.launch(job) {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is SearchIntent.SearchCity -> {
                        searchCity(it.key)
                    }
                    is SearchIntent.SaveCity -> {
                        saveCity(it.city)
                    }


                }
            }
        }
    }

    fun searchCity(key: String) {
        viewModelScope.launch(job) {
            searchCityUseCase(key)
                .onStart { showLoading() }
                .catch {
                    Log.e("TAG", it.message!!)
                    _state.value = SearchViewState.Error(it.message!!)
                }
                .onCompletion { hideLoading() }
                .collect {
                    when (it) {
                        is Result.Success -> {
                            _state.value = SearchViewState.Success(it.data!!)
                        }
                        is Result.Error -> {
                            _state.value = SearchViewState.Error(it.message)
                        }
                    }
                }
        }
    }

    fun clear(){
        job.cancel()
    }
    fun saveCity(city: CityResponse) {
        viewModelScope.launch(job) {
            saveCityLocalUseCase(City(city.id.toLong(), city.name, city.sys.country))
                .onStart { showLoading() }
                .catch {
                    _state.value = SearchViewState.Error(it.message!!)
                }
                .onCompletion { hideLoading() }
                .collect {
                    Log.e("TAG", "search dialog insert result is $it")
                    if(it != (-1).toLong()){
                        _state.value = SearchViewState.NavToCities(city)
                    }else {
                        _state.value = SearchViewState.Error("City is not saved in database")
                    }
                }
        }
    }

}