package uk.co.khaleelfreeman.spion.service.retrofit

import retrofit2.Call
import retrofit2.http.GET
import uk.co.khaleelfreeman.spion.service.HttpClient
import uk.co.khaleelfreeman.spion.service.retrofit.dto.ArticleResponse

class RetrofitClient : HttpClient {
    private val retrofit = RetrofitFactory.builder().build()
    override val service: Articles = retrofit.create(Articles::class.java)
}

interface Articles {
    @GET("liverpoolfc/articles")
    fun getArticles(): Call<ArticleResponse>
}