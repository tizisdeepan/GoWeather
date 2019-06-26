package com.deepan.goweather

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun parseDate(date: String): String = try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.isLenient = false
        val d = sdf.parse(date).time
        val c = Calendar.getInstance()
        c.timeInMillis = d
        when (c[Calendar.DAY_OF_WEEK]) {
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            Calendar.SUNDAY -> "Sunday"
            else -> ""
        }
    } catch (e: Exception) {
        ""
    }
}