package com.example.donkeyassessment

import com.example.donkeyassessment.data.weather.CurrentWeather
import com.example.donkeyassessment.data.weather.WeatherApi
import com.example.donkeyassessment.data.weather.WeatherRepository
import com.example.donkeyassessment.data.weather.WeatherResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class WeatherRepositoryTest {
    @Test
    fun getWeather_returnsApiResponse() = runTest {

        val fakeApi = object : WeatherApi {
            override suspend fun getWeather(
                latitude: Double,
                longitude: Double,
                current: String
            ): WeatherResponse {
                return WeatherResponse(
                    current = CurrentWeather(
                        temperature = 18.5,
                        weatherCode = 3
                    )
                )
            }
        }

        val repository = WeatherRepository(fakeApi)

        val result = repository.getWeather(
            latitude = 51.9244,
            longitude = 4.4777
        )

        assertEquals(18.5, result.current.temperature, 0.0)
        assertEquals(3, result.current.weatherCode)
    }
}