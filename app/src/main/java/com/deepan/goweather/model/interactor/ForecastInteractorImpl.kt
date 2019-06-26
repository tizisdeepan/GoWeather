package com.deepan.goweather.model.interactor

import android.util.Log
import com.deepan.goweather.ErrorTypes
import com.deepan.goweather.presenter.ForecastResponseCallback
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class ForecastInteractorImpl() : ForecastInteractor {

    override fun getForecast(location: String, callback: ForecastResponseCallback) {
        val request = Request.Builder().url("https://api.apixu.com/v1/forecast.json?key=817b6f22124f4a65abf153359192506&days=5&q=$location").get().build()
        Log.e("URL", request.url().toString())
        OkHttpClient.Builder().connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).writeTimeout(2, TimeUnit.MINUTES).build().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.getForecastDataOnFailure(ErrorTypes.API_CALL_ERROR)
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body()?.string()
                Log.e("Forecast Data", jsonData)
                try {
                    val forecasts = WeatherApiJSONParser.parseForecastData(jsonData)
                    if (forecasts.isNotEmpty()) callback.getForecastDataOnSuccess(forecasts)
                    else callback.getForecastDataOnFailure(ErrorTypes.EMPTY_DATA)
                } catch (e: Exception) {
                    e.printStackTrace()
                    callback.getForecastDataOnFailure(ErrorTypes.PARSER_ERROR)
                }
            }
        })
    }
}