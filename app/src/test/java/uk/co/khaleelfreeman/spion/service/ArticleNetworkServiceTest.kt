package uk.co.khaleelfreeman.spion.service

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Test
import java.awt.SystemColor.info
import java.io.IOException
import java.lang.String


class ArticleNetworkServiceTest {

    val service = ArticleNetworkService(httpClient = TestRetrofitClient())

    @Test
    fun execute() {
        service.execute {

        }
    }
}

class TestRetrofitClient : HttpClient {
    private val retrofit = RetrofitFactory.from(Environment.TEST)
    override val service: Articles = retrofit.create(Articles::class.java)
}
