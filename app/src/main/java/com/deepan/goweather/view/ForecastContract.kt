package com.deepan.goweather.view

import com.deepan.goweather.model.ForecastData

interface ForecastContract {
    fun showView(type: ViewType)
    fun setData(forecasts: ForecastData)
    fun showErrorPage()
    fun loadData()
    fun setupPermissions(doSomething: () -> Unit)
}