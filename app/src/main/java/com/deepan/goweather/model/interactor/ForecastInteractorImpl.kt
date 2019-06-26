package com.deepan.goweather.model.interactor

import com.deepan.goweather.ErrorTypes
import com.deepan.goweather.NetworkUtil
import com.deepan.goweather.R
import com.deepan.goweather.model.ForecastData
import com.deepan.goweather.presenter.ForecastPresenterImpl
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class ForecastInteractorImpl(var presenter: ForecastPresenterImpl) : ForecastInteractor {

    override fun getForecast(location: String, onSuccess: (ArrayList<ForecastData>) -> Unit, onFailure: (ErrorTypes) -> Unit) {
        if (NetworkUtil.isConnected(presenter.contract.getMyContext())) {
            val request = Request.Builder().url(presenter.contract.getMyContext().resources.getString(R.string.weather_api, presenter.contract.getMyContext().resources.getString(R.string.api_key), location)).get().build()
            OkHttpClient.Builder().connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).build().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure(ErrorTypes.API_CALL_ERROR)
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonData = response.body()?.string()
                    try {
                        val forecastData = WeatherApiJSONParser.parseForecastData(jsonData)
                        if (forecastData.isNotEmpty()) onSuccess(forecastData)
                        else onFailure(ErrorTypes.EMPTY_DATA)
                    } catch (e: Exception) {
                        onFailure(ErrorTypes.PARSER_ERROR)
                    }
                }
            })
        } else onFailure(ErrorTypes.NO_INTERNET)
    }
}