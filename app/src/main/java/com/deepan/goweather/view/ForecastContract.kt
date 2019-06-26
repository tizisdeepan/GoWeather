package com.deepan.goweather.view

import android.content.Context
import com.deepan.goweather.model.ForecastData

interface ForecastContract {
    fun getMyContext(): Context
    fun showView(type: Int)
    fun setData(forecasts: ArrayList<ForecastData>)
}