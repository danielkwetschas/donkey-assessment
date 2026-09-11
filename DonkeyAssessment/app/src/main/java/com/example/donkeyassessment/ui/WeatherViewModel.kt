package com.example.donkeyassessment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donkeyassessment.data.weather.WeatherRepository
import com.example.donkeyassessment.data.weather.WeatherRepositoryContract
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repo: WeatherRepositoryContract = WeatherRepository()
): ViewModel() {
    private val _temperature = MutableLiveData<Double>()
    val temperature: LiveData<Double> = _temperature

    private val _weatherCode = MutableLiveData<Int>()
    val weatherCode: LiveData<Int> = _weatherCode

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun loadWeather() {
        viewModelScope.launch {
            try {
                // Coörds for Gouda
                val weather = repo.getWeather(52.011112, 4.711111)

                _temperature.value = weather.current.temperature
                _weatherCode.value = weather.current.weatherCode
                _error.value = false
            } catch (e: Exception) {
                _error.value = true
            }
        }
    }
}