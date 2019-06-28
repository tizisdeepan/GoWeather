package com.deepan.goweather.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.deepan.goweather.R
import com.deepan.goweather.model.ForecastDay
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync

class ForecastAdapter(private var mForecasts: ArrayList<ForecastDay> = ArrayList()) : RecyclerView.Adapter<ForecastViewHolder>() {

    private lateinit var ctx: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        ctx = parent.context
        return ForecastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false))
    }

    override fun getItemCount(): Int = mForecasts.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.setData(ctx, mForecasts[holder.adapterPosition])
    }

    @ObsoleteCoroutinesApi
    private var eventActor = GlobalScope.actor<ArrayList<ForecastDay>>(capacity = Channel.CONFLATED) { for (list in channel) updateItemsInternal(list) }

    @ObsoleteCoroutinesApi
    fun updateItems(newList: ArrayList<ForecastDay>) {
        doAsync {
            val tempList: ArrayList<ForecastDay> = ArrayList()
            tempList.addAll(newList)
            eventActor.offer(tempList)
        }
    }

    private lateinit var diffResult: DiffUtil.DiffResult
    private fun updateItemsInternal(newList: ArrayList<ForecastDay>) {
        diffResult = DiffUtil.calculateDiff(DiffCallback(this@ForecastAdapter.mForecasts, newList))
        dispatchItems(newList, diffResult)
    }

    private fun dispatchItems(newList: ArrayList<ForecastDay>, diffResult: DiffUtil.DiffResult) {
        GlobalScope.launch(Dispatchers.Main) {
            diffResult.dispatchUpdatesTo(this@ForecastAdapter)
            mForecasts.clear()
            mForecasts.addAll(newList)
        }
    }

    class DiffCallback(private var oldList: ArrayList<ForecastDay>, private var newList: ArrayList<ForecastDay>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].averageTemperatureInCelsius.toString() + oldList[oldItemPosition].location == newList[newItemPosition].averageTemperatureInCelsius.toString() + newList[newItemPosition].location

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true

        @Nullable
        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? = super.getChangePayload(oldItemPosition, newItemPosition)
    }
}