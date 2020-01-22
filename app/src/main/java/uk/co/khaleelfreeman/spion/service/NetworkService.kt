package uk.co.khaleelfreeman.spion.service

import android.util.Log
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface NetworkService {
    fun execute(response: (ArticleResponse) -> Unit)
}

class ArticleNetworkService(private val httpClient: HttpClient = RetrofitClient()) :
    NetworkService {

    private val LOG_TAG = this.javaClass.name

    override fun execute(response: (ArticleResponse) -> Unit) {
        httpClient.service.getArticles().enqueue(object : Callback<ArticleResponse> {
            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.e(LOG_TAG, t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                response(ArticleResponse(
                    response.body()?.published ?: 0L,
                    response.body()?.articles ?: emptyArray()
                ))
            }
        })
    }
}

data class ArticleResponse(
    @SerializedName("feed_last_published")
    val published: Long,
    val articles: Array<Article>
)

data class Article(
    val title: String = "",
    val visual: Visual = Visual(),
    @SerializedName("published")
    val timeStamp: Long = 0L,
    @SerializedName("originId")
    val url: String = ""
)

data class Visual(val url: String = "")