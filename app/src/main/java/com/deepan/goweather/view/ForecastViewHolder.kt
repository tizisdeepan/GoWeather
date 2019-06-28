package com.deepan.goweather.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.deepan.goweather.R
import com.deepan.goweather.helpers.FontsConstants
import com.deepan.goweather.helpers.FontsHelper
import com.deepan.goweather.helpers.NumberFormatter
import com.deepan.goweather.model.ForecastDay
import kotlinx.android.synthetic.main.forecast_item.view.*

class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val dayLabel: TextView = view.dayLabel
    private val temperatureLabel: TextView = view.temperatureLabel

    @SuppressLint("SetTextI18n")
    fun setData(ctx: Context, forecastData: ForecastDay) {
        dayLabel.text = forecastData.date
        temperatureLabel.text = "${NumberFormatter.format(ctx, forecastData.averageTemperatureInCelsius)} ${ctx.resources.getString(R.string.symbol_celsius)}"

        dayLabel.typeface = FontsHelper[ctx, FontsConstants.REGULAR]
        temperatureLabel.typeface = FontsHelper[ctx, FontsConstants.REGULAR]
    }
}