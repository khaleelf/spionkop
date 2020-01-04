package uk.co.khaleelfreeman.spion.service

import android.util.Log
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NetworkService {
    fun execute(response: (ArticleResponse) -> Unit)
}

class ArticleNetworkService : NetworkService {

    private var res: ArticleResponse = ArticleResponse(
        0L,
        emptyArray()
    )

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.khaleelfreeman.co.uk/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create<ArticlesService>(
        ArticlesService::class.java
    )

    override fun execute(response: (ArticleResponse) -> Unit) {
        service.getArticles().enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<Response>,
                response: retrofit2.Response<Response>
            ) {
                response(ArticleResponse(
                    response.body()?.published ?: 0L,
                    response.body()?.articles ?: emptyArray()
                ))
            }
        })
    }
}

data class ArticleResponse(val published: Long, val articles: Array<Article>)

interface ArticlesService {
    @GET("liverpoolfc/articles")
    fun getArticles(): Call<Response>
}


data class Response(
    @SerializedName("feed_last_published")
    val published: Long,
    val articles: Array<Article>
)

data class Article(
    val title: String = "",
    val visual: Visual = Visual(),
    @SerializedName("published")
    val timeStamp: String = "",
    @SerializedName("originId")
    val url: String = ""
)

data class Visual(val url: String = "")