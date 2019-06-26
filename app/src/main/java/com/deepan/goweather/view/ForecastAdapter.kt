package com.deepan.goweather.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.deepan.goweather.R
import com.deepan.goweather.model.ForecastData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch

class ForecastAdapter(var forecasts: ArrayList<ForecastData>) : RecyclerView.Adapter<ForecastViewHolder>() {

    lateinit var ctx: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        ctx = parent.context
        return ForecastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false))
    }

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.setData(ctx, forecasts[holder.adapterPosition])
    }

    private var eventActor = GlobalScope.actor<ArrayList<ForecastData>>(capacity = Channel.CONFLATED) { for (list in channel) updateItemsInternal(list) }
    fun updateItems(newList: ArrayList<ForecastData>) {
        val tempList: ArrayList<ForecastData> = ArrayList()
        tempList.addAll(newList)
        eventActor.offer(tempList)
    }

    lateinit var diffResult: DiffUtil.DiffResult
    private fun updateItemsInternal(newList: ArrayList<ForecastData>) {
        diffResult = DiffUtil.calculateDiff(PostsDiffCallback(this@ForecastAdapter.forecasts, newList))
        dispatchItems(newList, diffResult)
    }

    fun dispatchItems(newList: ArrayList<ForecastData>, diffResult: DiffUtil.DiffResult) {
        GlobalScope.launch(Dispatchers.Default) {
            diffResult.dispatchUpdatesTo(this@ForecastAdapter)
            forecasts.clear()
            forecasts.addAll(newList)
        }
    }

    class PostsDiffCallback(var oldList: ArrayList<ForecastData>, var newList: ArrayList<ForecastData>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].date + oldList[oldItemPosition].averageTemperatureInCelcius == newList[newItemPosition].date + newList[newItemPosition].averageTemperatureInCelcius

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true

        @Nullable
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? = super.getChangePayload(oldItemPosition, newItemPosition)
    }

}