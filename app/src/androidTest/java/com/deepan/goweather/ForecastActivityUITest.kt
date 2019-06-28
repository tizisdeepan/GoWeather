package com.deepan.goweather

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.deepan.goweather.view.ForecastActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.StringEndsWith.endsWith
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ForecastActivityUITest {

    @get:Rule
    var activityRule: ActivityTestRule<ForecastActivity> = ActivityTestRule(ForecastActivity::class.java)

    @Before
    fun setUp() {
        Thread.sleep(5000)
    }

    @Test
    fun doUiTest(){
        onView(withId(R.id.currentTemperature)).check(matches(withText(endsWith("°"))))
        onView(withId(R.id.currentTemperature)).check(matches(withTextPattern()))
        onView(withRecyclerView(R.id.foreCastRecyclerView).atPositionOnView(0, R.id.temperatureLabel)).check(matches(withText(endsWith("° C"))))
        onView(withRecyclerView(R.id.foreCastRecyclerView).atPositionOnView(0, R.id.temperatureLabel)).check(matches(withTextPatternRecyclerViewItem()))
        onView(withRecyclerView(R.id.foreCastRecyclerView).atPositionOnView(0, R.id.dayLabel)).check(matches(withDayTextPatternRecyclerViewItem()))
    }

    private fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    private fun withTextPattern(): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            public override fun matchesSafely(view: View): Boolean {
                var text = (view as TextView).text.toString()
                text = text.replace("°", "")
                val degree = text.toFloat()
                return degree.toString() == String.format("%.1f", degree)
            }

            override fun describeTo(description: Description) {
                description.appendText("Text didn't match the valid pattern -> SinglePrecisionFloat°")
            }
        }
    }

    private fun withTextPatternRecyclerViewItem(): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            public override fun matchesSafely(view: View): Boolean {
                var text = (view as TextView).text.toString()
                text = text.replace("° C", "")
                val degree = text.toFloat()
                return degree.toString() == String.format("%.1f", degree)
            }

            override fun describeTo(description: Description) {
                description.appendText("Text didn't match the valid pattern -> SinglePrecisionFloat°")
            }
        }
    }

    private fun withDayTextPatternRecyclerViewItem(): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            public override fun matchesSafely(view: View): Boolean {
                val text = (view as TextView).text.toString()
                return text in arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
            }

            override fun describeTo(description: Description) {
                description.appendText("Text didn't match the valid pattern -> SinglePrecisionFloat°")
            }
        }
    }
}