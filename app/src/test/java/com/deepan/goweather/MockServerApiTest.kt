package com.deepan.goweather

import com.deepan.goweather.model.ForecastData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MockServerApiTest {
    val server = MockWebServer()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun startServerResponse() {
        val jsonData = """{"location": {"name": "Guduvancheri"},"forecast": {"forecastday":[{"date":"2019-06-27","day":{"avgtemp_c": 30.3,"avgtemp_f": 86.5}}]}}"""
        server.enqueue(MockResponse().setBody(jsonData))
        server.start()
        val baseUrl = server.url("v1/forecast.json")
        val response = makeCall(baseUrl)
        assertEquals(jsonData, response)
        server.shutdown()
    }

    @Test
    fun testJsonParser() {
        val fd = ForecastData(averageTemperatureInCelcius = 30.3f, averageTemperatureInFahrenheit = 86.5f, date = "Thursday", location = "Guduvancheri")
        val forecasts: ArrayList<ForecastData> = ArrayList()
        forecasts.add(fd)
        val json = Gson().toJson(forecasts)
        server.enqueue(MockResponse().setBody(json))
        server.start()
        val baseUrl = server.url("v1/forecast.json")
        val response = makeCall(baseUrl)
        //Unable to parse sample data, hence Gson is used here.
        val forecastsList = Gson().fromJson<ArrayList<ForecastData>>(response, object : TypeToken<ArrayList<ForecastData>>() {}.type)
        println(forecastsList.toString())
        assertTrue("JSONParser Failed", forecastsList.isNotEmpty())
        server.shutdown()
    }

    private fun makeCall(baseUrl: HttpUrl): String {
        val request = Request.Builder().url(baseUrl).get().build()
        request.header("Content-type=application/json; charset=utf-8")
        val response = OkHttpClient().newCall(request).execute()
        return response.body()?.string() ?: ""
    }
}