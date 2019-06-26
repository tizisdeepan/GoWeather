package com.deepan.goweather.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.deepan.goweather.R


class ForecastActivity : AppCompatActivity(), ForecastContract {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getMyContext(): Context = this
}
