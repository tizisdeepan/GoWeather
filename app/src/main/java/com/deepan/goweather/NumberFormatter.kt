package com.deepan.goweather

import android.content.Context

object NumberFormatter {
    fun format(ctx: Context, degree: Float): String = "${String.format("%.1f", degree)}${ctx.resources.getString(R.string.symbol_degree)}"
}