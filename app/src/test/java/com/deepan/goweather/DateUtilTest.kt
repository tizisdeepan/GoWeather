package com.deepan.goweather

import com.deepan.goweather.util.DateUtil
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DateUtilTest {

    @Test
    fun testDateUtilForMonday() {
        assertTrue("DateUtil Parser Failed for Monday", "Monday" == DateUtil.parseDate("2019-06-24"))
    }

    @Test
    fun testDateUtilForTuesday() {
        assertTrue("DateUtil Parser Failed for Tuesday", "Tuesday" == DateUtil.parseDate("2019-06-25"))
    }

    @Test
    fun testDateUtilForWednesday() {
        assertTrue("DateUtil Parser Failed for Wednesday", "Wednesday" == DateUtil.parseDate("2019-06-26"))
    }

    @Test
    fun testDateUtilForThursday() {
        assertTrue("DateUtil Parser Failed for Thursday", "Thursday" == DateUtil.parseDate("2019-06-27"))
    }

    @Test
    fun testDateUtilForFriday() {
        assertTrue("DateUtil Parser Failed for Friday", "Friday" == DateUtil.parseDate("2019-06-28"))
    }

    @Test
    fun testDateUtilForSaturday() {
        assertTrue("DateUtil Parser Failed for Saturday", "Saturday" == DateUtil.parseDate("2019-06-29"))
    }

    @Test
    fun testDateUtilForSunday() {
        assertTrue("DateUtil Parser Failed for Sunday", "Sunday" == DateUtil.parseDate("2019-06-30"))
    }

    @Test
    fun testDateUtilForWrongFormat() {
        assertTrue("DateUtil Parser Failed for Wrong Format", DateUtil.parseDate("30-12-2019").isEmpty())
    }
}