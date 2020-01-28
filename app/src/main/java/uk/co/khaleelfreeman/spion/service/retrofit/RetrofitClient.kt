package uk.co.khaleelfreeman.spion.service.retrofit

import co.uk.khaleelfreeman.service.retrofit.Articles
import co.uk.khaleelfreeman.service.retrofit.RetrofitFactory
import retrofit2.Call
import retrofit2.http.GET
import co.uk.khaleelfreeman.service.HttpClient
import co.uk.khaleelfreeman.service.retrofit.dto.ArticleResponse

class RetrofitClient : HttpClient {
    private val retrofit = RetrofitFactory.builder().build()
    override val service: Articles = retrofit.create(Articles::class.java)
}

interface Articles {
    @GET("liverpoolfc/articles")
    fun getArticles(): Call<ArticleResponse>
}