package uk.co.khaleelfreeman.spion.util

import org.junit.Assert.assertEquals
import org.junit.Test

class DateUtilTest {

    @Test
    fun `formatTimeStamp() should return valid date from article`(){
        val date = formatTimeStamp(1578574582000L)
        assertEquals(date, "Thu Jan 09")
    }
}