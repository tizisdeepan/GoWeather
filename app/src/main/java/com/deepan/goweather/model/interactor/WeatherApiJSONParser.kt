package com.deepan.goweather.model.interactor

import com.deepan.goweather.helpers.DateUtil
import com.deepan.goweather.model.ForecastData
import com.deepan.goweather.model.ForecastDay
import org.json.JSONObject
import java.lang.Exception

object WeatherApiJSONParser {
    fun parseForecastData(jsonData: String?): ForecastData {
        val forecasts = ForecastData()
        if (!jsonData.isNullOrEmpty()) {
            val jObject = JSONObject(jsonData)
            if (jObject.has("forecast")) {
                val forecastObject = jObject.optJSONObject("forecast")
                if (forecastObject.has("forecastday")) {
                    val forecastArray = forecastObject.optJSONArray("forecastday")
                    for (i in 0 until forecastArray.length()) {
                        if (i == 0) {
                            val fObject = forecastArray[i] as JSONObject
                            if (fObject.has("date")) forecasts.date = fObject.optString("date")
                            forecasts.date = DateUtil.parseDate(forecasts.date)
                            if (fObject.has("day")) {
                                val dayObject = fObject.optJSONObject("day")
                                if (dayObject.has("avgtemp_c")) forecasts.averageTemperatureInCelsius = try {
                                    dayObject.optString("avgtemp_c").toFloat()
                                } catch (e: Exception) {
                                    0f
                                }
                                if (dayObject.has("avgtemp_f")) forecasts.averageTemperatureInFahrenheit = try {
                                    dayObject.optString("avgtemp_f").toFloat()
                                } catch (e: Exception) {
                                    0f
                                }
                                if (jObject.has("location")) {
                                    val locationObject = jObject.optJSONObject("location")
                                    if (locationObject.has("name")) forecasts.location = locationObject.optString("name")
                                }
                            }
                        } else {
                            val fDay = ForecastDay()
                            val fObject = forecastArray[i] as JSONObject
                            if (fObject.has("date")) fDay.date = fObject.optString("date")
                            fDay.date = DateUtil.parseDate(fDay.date)
                            if (fObject.has("day")) {
                                val dayObject = fObject.optJSONObject("day")
                                if (dayObject.has("avgtemp_c")) fDay.averageTemperatureInCelsius = try {
                                    dayObject.optString("avgtemp_c").toFloat()
                                } catch (e: Exception) {
                                    0f
                                }
                                if (dayObject.has("avgtemp_f")) fDay.averageTemperatureInFahrenheit = try {
                                    dayObject.optString("avgtemp_f").toFloat()
                                } catch (e: Exception) {
                                    0f
                                }
                                if (jObject.has("location")) {
                                    val locationObject = jObject.optJSONObject("location")
                                    if (locationObject.has("name")) fDay.location = locationObject.optString("name")
                                }
                            }
                            forecasts.forecasts.add(fDay)
                        }
                    }
                }
            }
        }
        return forecasts
    }
}