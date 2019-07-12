package com.deepan.goweather.model.interactor

import com.deepan.goweather.model.ForecastData
import io.reactivex.Flowable

interface ForecastInteractor {
    fun getForecast(location: String): Flowable<ForecastData>
}