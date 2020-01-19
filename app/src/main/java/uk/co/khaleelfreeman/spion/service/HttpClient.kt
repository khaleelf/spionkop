package uk.co.khaleelfreeman.spion.service

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface HttpClient {
    val service: ArticlesService
}

class RetrofitClient : HttpClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.khaleelfreeman.co.uk/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    override val service: ArticlesService = retrofit.create<ArticlesService>(
        ArticlesService::class.java
    )
}

interface ArticlesService {
    @GET("liverpoolfc/articles")
    fun getArticles(): Call<ArticleResponse>
}