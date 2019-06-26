package com.deepan.goweather.model

data class ForecastData (
    var averageTemperatureInCelcius: Float = 0f,
    var averageTemperatureInFahrenheit: Float = 0f,
    var date: String = "",
    var location: String = ""
)