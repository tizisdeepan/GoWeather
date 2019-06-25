package com.deepan.goweather.model

import org.json.JSONObject

class WeatherApiJSONParser {
    fun parseForecastData(jsonData: String?) {
        if (!jsonData.isNullOrEmpty()) {
            val jObject = JSONObject(jsonData)
        }
    }
}