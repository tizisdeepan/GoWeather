package com.deepan.goweather.model

import com.deepan.goweather.ErrorTypes

interface ForecastInteractor {
    fun getForecast(location: String, onSuccess: (ArrayList<ForecastData>) -> Unit, onFailure: (ErrorTypes) -> Unit)
}