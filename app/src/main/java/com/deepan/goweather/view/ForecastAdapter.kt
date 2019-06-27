package com.deepan.goweather.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deepan.goweather.R
import com.deepan.goweather.model.ForecastData

class ForecastAdapter(var mForecasts: ArrayList<ForecastData> = ArrayList()) : RecyclerView.Adapter<ForecastViewHolder>() {

    fun setData(forecasts: List<ForecastData>) {
        mForecasts.clear()
        mForecasts.addAll(forecasts)
        notifyDataSetChanged()
    }

    private lateinit var ctx: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        ctx = parent.context
        return ForecastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false))
    }

    override fun getItemCount(): Int = mForecasts.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.setData(ctx, mForecasts[holder.adapterPosition])
    }
}