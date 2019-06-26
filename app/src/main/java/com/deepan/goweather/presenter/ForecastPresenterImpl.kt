package com.deepan.goweather.presenter

import com.deepan.goweather.ErrorTypes
import com.deepan.goweather.ViewType
import com.deepan.goweather.model.ForecastData
import com.deepan.goweather.model.interactor.ForecastInteractorImpl
import com.deepan.goweather.view.ForecastContract

class ForecastPresenterImpl(var contract: ForecastContract) : ForecastPresenter, ForecastResponseCallback {

    val interactor = ForecastInteractorImpl()

    override fun getForecastData(location: String) {
        interactor.getForecast(location, this)
    }

    override fun getForecastDataOnSuccess(forecasts: ArrayList<ForecastData>) {
        contract.setData(forecasts)
    }

    override fun getForecastDataOnFailure(error: ErrorTypes) {
        contract.showView(ViewType.SHOW_ERROR)
    }
}