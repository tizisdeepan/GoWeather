package com.deepan.goweather.presenter

import com.deepan.goweather.model.ForecastData
import com.deepan.goweather.model.interactor.ForecastInteractorImpl
import com.deepan.goweather.view.ErrorTypes
import com.deepan.goweather.view.ForecastContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

class ForecastPresenterImpl(private var contract: ForecastContract) : ForecastPresenter, ForecastResponseCallback {

    private val interact = ForecastInteractorImpl()
    private val disposables = CompositeDisposable()

    override fun getForecastData(location: String) {
        val disposable = interact.getForecast(location).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribeWith(object : DisposableSubscriber<ForecastData>() {
            override fun onComplete() {
                disposables.dispose()
            }

            override fun onNext(t: ForecastData?) {
                if (t != null) getForecastDataOnSuccess(t)
                else getForecastDataOnFailure(ErrorTypes.EMPTY_DATA)
            }

            override fun onError(t: Throwable?) {
                getForecastDataOnFailure(ErrorTypes.API_CALL_ERROR)
            }
        })
        disposables.add(disposable)
    }

    override fun getForecastDataOnSuccess(forecasts: ForecastData) {
        contract.setData(forecasts)
    }

    override fun getForecastDataOnFailure(error: ErrorTypes) {
        //Can handle each error type here
        contract.showErrorPage()
    }
}