package com.example.donkeyassessment.data.weather

interface WeatherRepositoryContract {
    suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): WeatherResponse
}
class WeatherRepository (
    private val api: WeatherApi = RetroFitInstance.weatherApi
): WeatherRepositoryContract {
    override suspend fun getWeather(latitude: Double, longitude: Double): WeatherResponse {
        return api.getWeather(latitude = latitude, longitude = longitude)
    }
}