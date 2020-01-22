package uk.co.khaleelfreeman.spion.service

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface HttpClient {
    val service: Articles
}

class RetrofitClient : HttpClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.khaleelfreeman.co.uk/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    override val service: Articles = retrofit.create(Articles::class.java)
}

interface Articles {
    @GET("liverpoolfc/articles")
    fun getArticles(): Call<ArticleResponse>
}