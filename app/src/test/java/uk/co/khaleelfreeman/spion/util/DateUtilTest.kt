package uk.co.khaleelfreeman.spion.util

import org.junit.Test

import org.junit.Assert.*
import uk.co.khaleelfreeman.spion.service.Article

class DateUtilTest {

    @Test
    fun `formatTimeStamp() should return valid date from article`(){
        val date = formatTimeStamp(1578574582000L)
        assertEquals(date, "Thu Jan 09")
    }
}