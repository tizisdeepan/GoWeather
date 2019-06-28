package com.deepan.goweather.model

data class ForecastDay(var averageTemperatureInCelsius: Float = 0f, var averageTemperatureInFahrenheit: Float = 0f, var date: String = "", var location: String = "")