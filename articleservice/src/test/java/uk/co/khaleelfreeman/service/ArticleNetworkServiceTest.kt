package uk.co.khaleelfreeman.service

import org.junit.Assert.assertEquals
import org.junit.Test
import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle
import uk.co.khaleelfreeman.spionkoparticledomain.util.formatTimeStamp


class ArticleNetworkServiceTest {

    private val service =
        ArticleNetworkService(
            TestHttpClient()
        )

    @Test
    fun `article service classes should represent json structure correctly`() {
        val articleResponse = service.execute().blockingGet()
        val actual = articleResponse.second[0]
        val expected = SpionkopArticle(
            url = "http://www.theguardian.com/football/2020/jan/23/the-psychology-of-football-rivalries",
            title = "The psychology of football rivalries",
            imageUrl = "https://i.guim.co.uk/img/media/ca30cd8ee93e58c292a3d14df19d10e083045d99/0_160_4957_2974/master/4957.jpg?width=300&quality=85&auto=format&fit=max&s=cba6dde8c361ee63fe13901ec03d57a0",
            date= formatTimeStamp(1579788648000L)
        )
        assertEquals(expected, actual)
    }
}

