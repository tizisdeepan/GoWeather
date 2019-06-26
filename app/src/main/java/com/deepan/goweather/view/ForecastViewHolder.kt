package com.deepan.goweather.view

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.deepan.goweather.FontsConstants
import com.deepan.goweather.FontsHelper
import com.deepan.goweather.NumberFormatter
import com.deepan.goweather.R
import com.deepan.goweather.model.ForecastData
import kotlinx.android.synthetic.main.forecast_item.view.*

class ForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val dayLabel = view.dayLabel
    val temperatureLabel = view.temperatureLabel

    fun setData(ctx: Context, forecastData: ForecastData) {
        dayLabel.text = forecastData.date
        temperatureLabel.text = "${NumberFormatter.format(ctx, forecastData.averageTemperatureInCelcius)} ${ctx.resources.getString(R.string.symbol_celcius)}"

        dayLabel.typeface = FontsHelper[ctx, FontsConstants.REGULAR]
        temperatureLabel.typeface = FontsHelper[ctx, FontsConstants.REGULAR]
    }
}