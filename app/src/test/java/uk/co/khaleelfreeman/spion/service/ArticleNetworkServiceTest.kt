package uk.co.khaleelfreeman.spion.service

import junit.framework.Assert.assertEquals
import okhttp3.OkHttpClient
import okhttp3.mock.*
import okhttp3.mock.ClasspathResources.resource
import okhttp3.mock.MediaTypes.MEDIATYPE_JSON
import org.junit.Test


class ArticleNetworkServiceTest {

    private val service = ArticleNetworkService(TestHttpClient())

    @Test
    fun `article service classes should represent json structure correctly`() {
        val articleResponse = service.execute().blockingGet()
        val actual = articleResponse.articles.first()
        val expected = Article(
            title = "The psychology of football rivalries",
            visual = Visual("https://i.guim.co.uk/img/media/ca30cd8ee93e58c292a3d14df19d10e083045d99/0_160_4957_2974/master/4957.jpg?width=300&quality=85&auto=format&fit=max&s=cba6dde8c361ee63fe13901ec03d57a0"),
            timeStamp = 1579788648000L,
            url = "http://www.theguardian.com/football/2020/jan/23/the-psychology-of-football-rivalries"
        )
        assertEquals(expected, actual)
    }
}

class TestHttpClient : HttpClient {
    private val interceptor = MockInterceptor().apply {
        rule(get, url eq "https://www.khaleelfreeman.co.uk/liverpoolfc/articles") {
            respond(resource("ExampleResponse.json"), MEDIATYPE_JSON)
        }
    }
    private val client =  OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()
    private val retrofit = RetrofitFactory.builder().client(client).build()

    override val service: Articles = retrofit.create(Articles::class.java)

}

