package com.deepan.goweather.presenter

import com.deepan.goweather.ErrorTypes
import com.deepan.goweather.model.ForecastData
import com.deepan.goweather.model.interactor.ForecastInteractorImpl
import com.deepan.goweather.view.ForecastContract

class ForecastPresenterImpl(var contract: ForecastContract) : ForecastPresenter {

    val interactor = ForecastInteractorImpl(this)

    override fun getForecastData(location: String) {
        interactor.getForecast(location, this::getForecastDataOnSuccess, this::getForecastDataOnFailure)
    }

    private fun getForecastDataOnSuccess(forecastData: ArrayList<ForecastData>) {

    }

    private fun getForecastDataOnFailure(error: ErrorTypes) {

    }
}