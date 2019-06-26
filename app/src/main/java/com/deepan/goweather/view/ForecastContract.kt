package com.deepan.goweather.view

import android.content.Context
import com.deepan.goweather.ViewType

interface ForecastContract {
    fun getMyContext(): Context
    fun showView(type: ViewType)
}