package com.deepan.goweather.helpers

import android.content.Context
import com.deepan.goweather.R

object NumberFormatter {
    fun format(ctx: Context, degree: Float): String = "${String.format("%.1f", degree)}${ctx.resources.getString(R.string.symbol_degree)}"
}