package com.deepan.goweather

import android.os.Handler
import com.deepan.goweather.model.ForecastData
import com.deepan.goweather.model.interactor.ForecastInteractor
import com.deepan.goweather.presenter.ForecastPresenter
import com.deepan.goweather.presenter.ForecastResponseCallback
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MockWeatherApi {

    @Mock
    private lateinit var mockInteractor: ForecastInteractor
    @Mock
    private lateinit var callback: ForecastResponseCallback

    val forecasts: ArrayList<ForecastData> = ArrayList()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun mockWeatherApiSuccess() {
        Mockito.doAnswer {
            (it.arguments[1] as ForecastResponseCallback).getForecastDataOnSuccess(forecasts)
        }.`when`(mockInteractor).getForecast("12.82867455,80.0513619", callback)
        mockInteractor.getForecast("12.82867455,80.0513619", callback)
        Mockito.verify(callback, times(1)).getForecastDataOnSuccess(forecasts)
    }

    @Test
    fun mockWeatherApiFailureByParserError() {
        Mockito.doAnswer {
            (it.arguments[1] as ForecastResponseCallback).getForecastDataOnFailure(ErrorTypes.PARSER_ERROR)
        }.`when`(mockInteractor).getForecast("12.82867455,80.0513619", callback)
        mockInteractor.getForecast("12.82867455,80.0513619", callback)
        Mockito.verify(callback, times(1)).getForecastDataOnFailure(ErrorTypes.PARSER_ERROR)
    }

    @Test
    fun mockWeatherApiFailureByApiError() {
        Mockito.doAnswer {
            (it.arguments[1] as ForecastResponseCallback).getForecastDataOnFailure(ErrorTypes.API_CALL_ERROR)
        }.`when`(mockInteractor).getForecast("12.82867455,80.0513619", callback)
        mockInteractor.getForecast("12.82867455,80.0513619", callback)
        Mockito.verify(callback, times(1)).getForecastDataOnFailure(ErrorTypes.API_CALL_ERROR)
    }

    @Test
    fun mockWeatherApiFailureByEmptyData() {
        Mockito.doAnswer {
            (it.arguments[1] as ForecastResponseCallback).getForecastDataOnFailure(ErrorTypes.EMPTY_DATA)
        }.`when`(mockInteractor).getForecast("12.82867455,80.0513619", callback)
        mockInteractor.getForecast("12.82867455,80.0513619", callback)
        Mockito.verify(callback, times(1)).getForecastDataOnFailure(ErrorTypes.EMPTY_DATA)
    }
}