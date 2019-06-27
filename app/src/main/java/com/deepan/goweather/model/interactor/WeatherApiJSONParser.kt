package com.deepan.goweather.model.interactor

import com.deepan.goweather.helpers.DateUtil
import com.deepan.goweather.model.ForecastData
import org.json.JSONObject
import java.lang.Exception

object WeatherApiJSONParser {
    fun parseForecastData(jsonData: String?): ArrayList<ForecastData> {
        val forecasts: ArrayList<ForecastData> = ArrayList()
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
                        fData.date = DateUtil.parseDate(fData.date)
                        if (fObject.has("day")) {
                            val dayObject = fObject.optJSONObject("day")
                            if (dayObject.has("avgtemp_c")) fData.averageTemperatureInCelcius = try {
                                dayObject.optString("avgtemp_c").toFloat()
                            } catch (e: Exception) {
                                0f
                            }
                            if (dayObject.has("avgtemp_f")) fData.averageTemperatureInFahrenheit = try {
                                dayObject.optString("avgtemp_f").toFloat()
                            } catch (e: Exception) {
                                0f
                            }
                            if (jObject.has("location")) {
                                val locationObject = jObject.optJSONObject("location")
                                if (locationObject.has("name")) fData.location = locationObject.optString("name")
                            }
                            forecasts.add(fData)
                        }
                    }
                }
            }
        }
        return forecasts
    }
}