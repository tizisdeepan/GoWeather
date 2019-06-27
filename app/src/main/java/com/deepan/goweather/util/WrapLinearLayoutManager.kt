package com.deepan.goweather.util

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class WrapLinearLayoutManager(ctx: Context) : androidx.recyclerview.widget.LinearLayoutManager(ctx) {
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Log.e("RecyclerViewError", "IndexOutOfBoundsException in RecyclerView happens")
        } catch (e: Exception) {
            Log.e("RecyclerViewError", e.toString())
        }
    }
}
