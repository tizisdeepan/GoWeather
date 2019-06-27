package com.deepan.goweather.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.deepan.goweather.*
import com.deepan.goweather.model.ForecastData
import com.deepan.goweather.presenter.ForecastPresenterImpl
import kotlinx.android.synthetic.main.activity_forecast.*
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.deepan.goweather.model.ForecastDataViewModel
import com.deepan.goweather.helpers.*


class ForecastActivity : AppCompatActivity(), ForecastContract {

    private var presenter: ForecastPresenterImpl? = null
    private lateinit var forecastsLiveData: ForecastDataViewModel
    private val requestCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        foreCastRecyclerView.layoutManager = WrapLinearLayoutManager(this)
        foreCastRecyclerView.itemAnimator = null
        if (foreCastRecyclerView.adapter == null) foreCastRecyclerView.adapter = ForecastAdapter()

        forecastsLiveData = ViewModelProviders.of(this).get(ForecastDataViewModel::class.java)
        forecastsLiveData.forecasts.observe(this, Observer<ArrayList<ForecastData>> { forecasts ->
            this@ForecastActivity.runOnUiThread {
                runOnUiThread {
                    if (forecasts.isNotEmpty()) {
                        showView(ViewType.SHOW_DATA)
                        val currentForeCast = forecasts[0]
                        currentTemperature.text = NumberFormatter.format(this, currentForeCast.averageTemperatureInCelcius)
                        currentLocation.text = currentForeCast.location
                        foreCastRecyclerView.layoutManager = WrapLinearLayoutManager(this)
                        foreCastRecyclerView.itemAnimator = null
                        if (foreCastRecyclerView.adapter == null) foreCastRecyclerView.adapter = ForecastAdapter()
                        (foreCastRecyclerView.adapter as? ForecastAdapter)?.setData(forecasts.takeLast(forecasts.size - 1))
                        foreCastRecyclerView.animation = AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom)
                        currentTemperature.animation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
                    } else showView(ViewType.SHOW_ERROR)
                }
            }
        })

        initViews()

        retryButton.setOnClickListener {
            initViews()
        }

        setFonts()
    }

    private fun setFonts() {
        errorText.typeface = FontsHelper[this, FontsConstants.THIN]
        retryButton.typeface = FontsHelper[this, FontsConstants.REGULAR]
        currentTemperature.typeface = FontsHelper[this, FontsConstants.BLACK]
        currentLocation.typeface = FontsHelper[this, FontsConstants.THIN]
    }

    private fun initViews() {
        if (presenter == null) presenter = ForecastPresenterImpl(this)
        showView(ViewType.SHOW_LOADER)
        if (forecastsLiveData.forecasts.value == null) {
            setupPermissions {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    val location = LocationService(this).getLocation(LocationManager.GPS_PROVIDER)
                    if (location != null && NetworkUtil.isConnected(this)) presenter?.getForecastData("${location.latitude},${location.longitude}")
                    else showView(ViewType.SHOW_ERROR)
                }
            }
        } else forecastsLiveData.loadForecasts(forecastsLiveData.forecasts.value ?: ArrayList())
    }

    override fun showView(type: Int) {
        runOnUiThread {
            when (type) {
                ViewType.SHOW_LOADER -> {
                    loaderFrame.visibility = View.VISIBLE
                    dataFrame.visibility = View.GONE
                    errorFrame.visibility = View.GONE
                }
                ViewType.SHOW_DATA -> {
                    loaderFrame.visibility = View.GONE
                    dataFrame.visibility = View.VISIBLE
                    errorFrame.visibility = View.GONE
                }
                ViewType.SHOW_ERROR -> {
                    loaderFrame.visibility = View.GONE
                    dataFrame.visibility = View.GONE
                    errorFrame.visibility = View.VISIBLE
                }
                else -> {
                    loaderFrame.visibility = View.GONE
                    dataFrame.visibility = View.GONE
                    errorFrame.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun setData(forecasts: ArrayList<ForecastData>) {
        runOnUiThread {
            forecastsLiveData.loadForecasts(forecasts)
        }
    }

    override fun getMyContext(): Context = this

    lateinit var dothis: () -> Unit
    private fun setupPermissions(doSomething: () -> Unit) {
        val locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        dothis = doSomething
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)
        } else dothis()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            this.requestCode -> {
                if (grantResults.isEmpty() || grantResults.any { it != PackageManager.PERMISSION_GRANTED }) {
                    PermissionsDialog(this@ForecastActivity, "To continue, give GoWeather access to your Location service.").show()
                } else dothis()
            }
        }
    }
}
