package com.example.donkeyassessment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.donkeyassessment.data.weather.CurrentWeather
import com.example.donkeyassessment.data.weather.WeatherRepositoryContract
import com.example.donkeyassessment.data.weather.WeatherResponse
import com.example.donkeyassessment.ui.WeatherViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadWeather_success_setsWeatherData() = runTest {
        val fakeRepository = object : WeatherRepositoryContract {

            override suspend fun getWeather(
                latitude: Double,
                longitude: Double
            ): WeatherResponse {
                return WeatherResponse(
                    current = CurrentWeather(
                        temperature = 18.5,
                        weatherCode = 3
                    )
                )
            }
        }

        val viewModel = WeatherViewModel(fakeRepository)

        viewModel.loadWeather()

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(18.5, viewModel.temperature.value!!, 0.0)
        assertEquals(3, viewModel.weatherCode.value)
        assertEquals(false, viewModel.error.value)
    }

    @Test
    fun loadWeather_error_setsErrorTrue() = runTest {
        val fakeRepository = object : WeatherRepositoryContract {

            override suspend fun getWeather(
                latitude: Double,
                longitude: Double
            ): WeatherResponse {
                throw Exception("API error")
            }
        }

        val viewModel = WeatherViewModel(fakeRepository)

        viewModel.loadWeather()

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, viewModel.error.value)
    }
}