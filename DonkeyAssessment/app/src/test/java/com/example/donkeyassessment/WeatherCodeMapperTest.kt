package com.example.donkeyassessment

import com.example.donkeyassessment.data.weather.WeatherCodeMapper
import junit.framework.TestCase.assertEquals
import org.junit.Test


class WeatherCodeMapperTest {

    @Test
    fun codeZero_ClearSky() {
        val result = WeatherCodeMapper.getDescription(0)

        assertEquals("Open lucht", result)
    }

    @Test
    fun codeOne_PartlyCloudy() {
        val result = WeatherCodeMapper.getDescription(1)

        assertEquals("Deels bewolkt", result)
    }

    @Test
    fun codeThree_Cloudy() {
        val result = WeatherCodeMapper.getDescription(3)

        assertEquals("Bewolkt", result)
    }

    @Test
    fun rainCode_Rain() {
        val result = WeatherCodeMapper.getDescription(63)

        assertEquals("Regen", result)
    }

    @Test
    fun snowCode_Snow() {
        val result = WeatherCodeMapper.getDescription(73)

        assertEquals("Sneeuw", result)
    }

    @Test
    fun thunderstormCode_Thunderstorm() {
        val result = WeatherCodeMapper.getDescription(95)

        assertEquals("Onweersbui", result)
    }

    @Test
    fun unknownCode_UnknownWeather() {
        val result = WeatherCodeMapper.getDescription(999)

        assertEquals("Onbekend", result)
    }
}