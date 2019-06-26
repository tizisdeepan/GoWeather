package com.deepan.goweather.presenter

import com.deepan.goweather.ErrorTypes
import com.deepan.goweather.model.ForecastData

interface ForecastResponseCallback {
    fun getForecastDataOnSuccess(forecasts: ArrayList<ForecastData>)
    fun getForecastDataOnFailure(error: ErrorTypes)
}