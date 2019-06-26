package com.deepan.goweather.model.interactor

import com.deepan.goweather.ErrorTypes
import com.deepan.goweather.model.ForecastData

interface ForecastInteractor {
    fun getForecast(location: String, onSuccess: (ArrayList<ForecastData>) -> Unit, onFailure: (ErrorTypes) -> Unit)
}