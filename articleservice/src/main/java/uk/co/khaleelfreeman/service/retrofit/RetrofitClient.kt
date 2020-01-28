package uk.co.khaleelfreeman.service.retrofit

import uk.co.khaleelfreeman.service.HttpClient
import uk.co.khaleelfreeman.service.retrofit.dto.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET

class RetrofitClient : HttpClient {
    private val retrofit = RetrofitFactory.builder().build()
    override val service: Articles = retrofit.create(Articles::class.java)
}

interface Articles {
    @GET("liverpoolfc/articles")
    fun getArticles(): Call<ArticleResponse>
}