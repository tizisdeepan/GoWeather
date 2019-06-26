package com.deepan.goweather.presenter

import com.deepan.goweather.ErrorTypes
import com.deepan.goweather.ViewType
import com.deepan.goweather.model.ForecastData
import com.deepan.goweather.model.interactor.ForecastInteractorImpl
import com.deepan.goweather.view.ForecastContract

class ForecastPresenterImpl(var contract: ForecastContract) : ForecastPresenter {

    val interactor = ForecastInteractorImpl(this)

    override fun getForecastData(location: String) {
        interactor.getForecast(location, this::getForecastDataOnSuccess, this::getForecastDataOnFailure)
    }

    private fun getForecastDataOnSuccess(forecasts: ArrayList<ForecastData>) {
        contract.setData(forecasts)
    }

    private fun getForecastDataOnFailure(error: ErrorTypes) {
        contract.showView(ViewType.SHOW_ERROR)
    }
}