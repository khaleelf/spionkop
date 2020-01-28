package uk.co.khaleelfreeman.spion

import okhttp3.OkHttpClient
import okhttp3.mock.*
import uk.co.khaleelfreeman.spion.service.HttpClient
import uk.co.khaleelfreeman.spion.service.retrofit.Articles
import uk.co.khaleelfreeman.spion.service.retrofit.RetrofitFactory

class TestHttpClient : HttpClient {
    private val interceptor = MockInterceptor().apply {
        rule(get, url eq "https://www.khaleelfreeman.co.uk/liverpoolfc/articles") {
            respond(ClasspathResources.resource("ExampleResponse.json"), MediaTypes.MEDIATYPE_JSON)
        }
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
    private val retrofit = RetrofitFactory.builder().client(client).build()

    override val service: Articles = retrofit.create(Articles::class.java)

}