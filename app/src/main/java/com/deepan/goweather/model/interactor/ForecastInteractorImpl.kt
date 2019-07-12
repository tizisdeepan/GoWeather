package com.deepan.goweather.model.interactor

import android.util.Log
import com.deepan.goweather.helpers.ApiCalls
import com.deepan.goweather.model.ForecastData
import com.deepan.goweather.view.ErrorTypes
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ForecastInteractorImpl : ForecastInteractor {

    override fun getForecast(location: String): Flowable<ForecastData> {
        return Flowable.create({
            val request = Request.Builder().url("https://api.apixu.com/v1/forecast.json?key=817b6f22124f4a65abf153359192506&days=5&q=$location").get().build()
            ApiCalls().myClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonData = response.body?.string()
                    Log.e("Forecast Data", jsonData)
                    try {
                        val forecasts = WeatherApiJSONParser.parseForecastData(jsonData)
                        if (forecasts.forecasts.isNotEmpty()) it.onNext(forecasts)
                        else it.onError(Throwable(ErrorTypes.EMPTY_DATA.toString()))
                    } catch (e: Exception) {
                        e.printStackTrace()
                        it.onError(e)
                    } finally {
                        it.onComplete()
                    }
                }
            })
        }, BackpressureStrategy.BUFFER)
    }
}