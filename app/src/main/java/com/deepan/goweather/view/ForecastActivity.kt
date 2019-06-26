package com.deepan.goweather.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.deepan.goweather.R
import com.deepan.goweather.ViewType
import com.deepan.goweather.presenter.ForecastPresenterImpl
import kotlinx.android.synthetic.main.activity_main.*


class ForecastActivity : AppCompatActivity(), ForecastContract {

    lateinit var presenter: ForecastPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = ForecastPresenterImpl(this)
        showView(ViewType.LOADER)

    }

    override fun showView(type: ViewType) {
        when (type) {
            ViewType.LOADER -> {
                loaderFrame.visibility = View.VISIBLE
                dataFrame.visibility = View.GONE
                errorFrame.visibility = View.GONE
            }
            ViewType.DATA -> {
                loaderFrame.visibility = View.GONE
                dataFrame.visibility = View.VISIBLE
                errorFrame.visibility = View.GONE
            }
            ViewType.ERROR -> {
                loaderFrame.visibility = View.GONE
                dataFrame.visibility = View.GONE
                errorFrame.visibility = View.VISIBLE
            }
        }
    }

    override fun getMyContext(): Context = this
}
