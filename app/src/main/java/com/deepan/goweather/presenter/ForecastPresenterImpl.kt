package com.deepan.goweather.presenter

import com.deepan.goweather.view.ErrorTypes
import com.deepan.goweather.view.ViewType
import com.deepan.goweather.model.ForecastData
import com.deepan.goweather.model.interactor.ForecastInteractorImpl
import com.deepan.goweather.view.ForecastContract

class ForecastPresenterImpl(private var contract: ForecastContract) : ForecastPresenter, ForecastResponseCallback {

    private val interact = ForecastInteractorImpl()

    override fun getForecastData(location: String) {
        interact.getForecast(location, this)
    }

    override fun getForecastDataOnSuccess(forecasts: ArrayList<ForecastData>) {
        contract.setData(forecasts)
    }

    override fun getForecastDataOnFailure(error: ErrorTypes) {
        //Can handle each error type here
        contract.showView(ViewType.SHOW_ERROR)
    }
}