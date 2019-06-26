package com.deepan.goweather.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepan.goweather.FontsConstants
import com.deepan.goweather.FontsHelper
import com.deepan.goweather.R
import com.deepan.goweather.ViewType
import com.deepan.goweather.model.ForecastData
import com.deepan.goweather.model.interactor.LocationService
import com.deepan.goweather.presenter.ForecastPresenterImpl
import kotlinx.android.synthetic.main.activity_main.*


class ForecastActivity : AppCompatActivity(), ForecastContract {

    var presenter: ForecastPresenterImpl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        foreCastRecyclerView.layoutManager = LinearLayoutManager(this)
        foreCastRecyclerView.itemAnimator = null
        setupPermissions {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                val location = LocationService(this).getLocation(LocationManager.GPS_PROVIDER)
                if (location != null) presenter?.getForecastData("${location.latitude},${location.longitude}")
                else showView(ViewType.SHOW_ERROR)
            }
        }
    }

    override fun showView(type: Int) {
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

    override fun setData(forecasts: ArrayList<ForecastData>) {
        this.runOnUiThread {
            if (forecasts.isNotEmpty()) {
                Log.e("FORECASTS", forecasts.toString())
                showView(ViewType.SHOW_DATA)
                val currentForeCast = forecasts[0]
                currentTemperature.text = String.format("%.1f", currentForeCast.averageTemperatureInCelcius) + resources.getString(R.string.symbol_degree)
                currentLocation.text = currentForeCast.location
                forecasts.removeAt(0)

            } else showView(ViewType.SHOW_ERROR)
        }
    }

    override fun getMyContext(): Context = this

    private val PERMISSIONS_REQUEST_CODE = 101
    lateinit var dothis: () -> Unit
    private fun setupPermissions(doSomething: () -> Unit) {
        val locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        dothis = doSomething
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSIONS_REQUEST_CODE)
        } else dothis()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults.any { it != PackageManager.PERMISSION_GRANTED }) {
                    PermissionsDialog(this@ForecastActivity, "To continue, give GoWeather access to your Location service.").show()
                } else dothis()
            }
        }
    }
}
