package com.deepan.goweather.model.interactor

import com.deepan.goweather.presenter.ForecastResponseCallback

interface ForecastInteractor {
    fun getForecast(location: String, callback: ForecastResponseCallback)
}