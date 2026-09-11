package com.example.donkeyassessment.data.weather

object WeatherCodeMapper {

    fun getDescription(code: Int): String {
        return when (code) {
            0 -> "Open lucht"
            1, 2 -> "Deels bewolkt"
            3 -> "Bewolkt"
            45, 48 -> "Mist"
            51, 53, 55 -> "Miezer"
            61, 63, 65 -> "Regen"
            71, 73, 75 -> "Sneeuw"
            80, 81, 82 -> "Regenbuien"
            95, 96, 99 -> "Onweersbui"
            else -> "Onbekend"
        }
    }
}