package com.deepan.goweather

import androidx.test.rule.ActivityTestRule
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestUrl {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testUrlWithKeyAndLocation() {
        val city = "Bangalore"
        val urlHardcoded = "https://api.apixu.com/v1/forecast.json?key=817b6f22124f4a65abf153359192506&days=4&q=$city"
        val urlFromStringResources = activityRule.activity.resources.getString(
            R.string.weather_api,
            activityRule.activity.resources.getString(R.string.api_key),
            city
        )
        assertTrue("URL Validation failed", urlHardcoded == urlFromStringResources)
    }
}