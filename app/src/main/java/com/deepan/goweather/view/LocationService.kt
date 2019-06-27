package com.deepan.goweather.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat

class LocationService(var context: Context) : LocationListener {

    private var locationManager: LocationManager? = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    var location: Location? = null
    private val minDistanceForUpdate = 10.toFloat()
    private val minTimeForUpdate = (1000 * 60 * 2).toLong()

    override fun onLocationChanged(location: Location?) {
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

    fun getLocation(provider: String): Location? {
        if (locationManager?.isProviderEnabled(provider) == true) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager?.requestLocationUpdates(provider, minTimeForUpdate, minDistanceForUpdate, this)
                if (locationManager != null) {
                    location = locationManager?.getLastKnownLocation(provider)
                    return location
                }
            }
        }
        return null
    }
}