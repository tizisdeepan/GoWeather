package com.deepan.goweather

import androidx.test.rule.ActivityTestRule
import com.deepan.goweather.helpers.NumberFormatter
import com.deepan.goweather.view.ForecastActivity
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse
import org.junit.Rule
import org.junit.Test

@RunWith(JUnit4::class)
class NumberFormatterTest {

    @get:Rule
    var activityRule: ActivityTestRule<ForecastActivity> = ActivityTestRule(ForecastActivity::class.java)

    @Test
    fun testNumberFormatterTrueCase() {
        assertTrue("NumberFormatter Failed", "24.5°" == NumberFormatter.format(activityRule.activity.applicationContext, 24.54321f))
    }

    @Test
    fun testNumberFormatterFalseCase() {
        assertFalse("NumberFormatter Failed", "24.5 °" == NumberFormatter.format(activityRule.activity.applicationContext, 24.54321f))
    }

    @Test
    fun testNumberFormatterFalseCaseDoublePrecision() {
        assertFalse("NumberFormatter Failed", "24.55°" == NumberFormatter.format(activityRule.activity.applicationContext, 24.54321f))
    }
}