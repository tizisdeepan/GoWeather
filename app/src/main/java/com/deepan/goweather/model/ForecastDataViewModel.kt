package com.deepan.goweather.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData


class ForecastDataViewModel : ViewModel() {
    private var mForecasts: MutableLiveData<ForecastData> = MutableLiveData()

    val forecasts: MutableLiveData<ForecastData>
        get() {
            return mForecasts
        }

    fun loadForecasts(forecasts: ForecastData?) {
        if (forecasts != null) mForecasts.value = forecasts
    }
}