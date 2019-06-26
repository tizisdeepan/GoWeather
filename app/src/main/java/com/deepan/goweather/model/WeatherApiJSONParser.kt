package com.deepan.goweather.model

import org.json.JSONObject
import java.lang.Exception

object WeatherApiJSONParser {
    fun parseForecastData(jsonData: String?): ArrayList<ForecastData> {
        val forecastData: ArrayList<ForecastData> = ArrayList()
        if (!jsonData.isNullOrEmpty()) {
            val jObject = JSONObject(jsonData)
            if (jObject.has("forecast")) {
                val forecastObject = jObject.optJSONObject("forecast")
                if (forecastObject.has("forecastday")) {
                    val forecastArray = forecastObject.optJSONArray("forecastday")
                    for (i in 0 until forecastArray.length()) {
                        val fObject = forecastArray[i] as JSONObject
                        val fData = ForecastData()
                        if (fObject.has("date")) fData.date = fObject.optString("date")
                        if (fObject.has("avgtemp_c")) fData.averageTemperatureInCelcius = try {
                            fObject.optString("avgtemp_c").toFloat()
                        } catch (e: Exception) {
                            0f
                        }
                        if (fObject.has("avgtemp_f")) fData.averageTemperatureInFahrenheit = try {
                            fObject.optString("avgtemp_f").toFloat()
                        } catch (e: Exception) {
                            0f
                        }
                        if (jObject.has("location")) {
                            val locationObject = jObject.optJSONObject("location")
                            if (locationObject.has("name")) fData.location = locationObject.optString("name")
                        }
                        forecastData.add(fData)
                    }
                }
            }
        }
        return forecastData
    }
}