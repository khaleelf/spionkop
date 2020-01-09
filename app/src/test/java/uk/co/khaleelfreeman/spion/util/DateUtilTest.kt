package uk.co.khaleelfreeman.spion.util

import org.junit.Test

import org.junit.Assert.*
import uk.co.khaleelfreeman.spion.service.Article

class DateUtilTest {

    @Test
    fun `formatDate() should return valid date from article`(){
        val date = formatDate(Article(timeStamp = "1578574582000"))
        assertEquals(date, "Thu Jan 09")
    }
}