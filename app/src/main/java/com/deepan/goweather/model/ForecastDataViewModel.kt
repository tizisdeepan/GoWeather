package com.deepan.goweather.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData


class ForecastDataViewModel : ViewModel() {
    private var mForecasts: MutableLiveData<ArrayList<ForecastData>> = MutableLiveData()

    val forecasts: MutableLiveData<ArrayList<ForecastData>>
        get() {
            return mForecasts
        }

    fun loadForecasts(forecasts: ArrayList<ForecastData>) {
        mForecasts.value = forecasts
    }
}