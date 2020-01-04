package uk.co.khaleelfreeman.spion

import android.util.Log
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Repository {
    fun getArticles(): Array<Article>
    fun fetchArticles(callback: () -> Unit)
}

object ArticleRepository : Repository {

    private val THIRTY_MIN_IN_MILLIS = DateTime(1800000L)

    var published: Long = 0L
        private set(value) {
            field = value
        }

    private var articles: Array<Article> = emptyArray()

    override fun getArticles(): Array<Article> {
        return articles.clone()
    }

    override fun fetchArticles(callback: () -> Unit) {
        if (currentTimeMinusPublished() > THIRTY_MIN_IN_MILLIS) {
            executeNetworkRequest(callback)
        } else {
            callback()
        }
    }

    private fun executeNetworkRequest(callback: () -> Unit) {

        // TODO: Look at extracting out into a network service.

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.khaleelfreeman.co.uk/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create<ArticlesService>(ArticlesService::class.java)

        service.getArticles().enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("", t.localizedMessage)
                articles =
                    arrayOf(Article("Sorry there was an error fetching the articles", Visual()))
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                published = response.body()?.published ?: 0L
                articles = response.body()?.articles
                    ?: arrayOf(Article("Sorry there was an error fetching the articles", Visual()))
                callback()
            }
        })
    }

    private fun currentTimeMinusPublished() = DateTime().toDateTime(DateTimeZone.UTC).minus(published)
}

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