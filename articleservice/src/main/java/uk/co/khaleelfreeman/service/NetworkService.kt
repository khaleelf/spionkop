package uk.co.khaleelfreeman.service

import android.util.Log
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.khaleelfreeman.service.domain.SpionkopArticle
import uk.co.khaleelfreeman.service.retrofit.dto.Article
import uk.co.khaleelfreeman.service.retrofit.dto.ArticleResponse
import uk.co.khaleelfreeman.service.util.formatTimeStamp

interface NetworkService {
    fun execute(): Single<Pair<Long, List<SpionkopArticle>>>
}

class ArticleNetworkService(private val httpClient: HttpClient) :
    NetworkService {

    private val LOG_TAG = this.javaClass.name

    override fun execute(): Single<Pair<Long, List<SpionkopArticle>>> {

        return Single.create<Pair<Long, List<SpionkopArticle>>> { single ->
            httpClient.service.getArticles().enqueue(object : Callback<ArticleResponse> {
                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    Log.e(LOG_TAG, t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ArticleResponse>,
                    response: Response<ArticleResponse>
                ) {
                    single.onSuccess(
                        Pair(
                            response.body()?.published ?: 0L,
                            response.body()?.articles?.map(::articleToSpionkopArticle) ?: emptyList()
                        )
                    )
                }
            })
        }
    }
}


// TODO: Move to separate file.
private fun articleToSpionkopArticle(article: Article): SpionkopArticle {
    return SpionkopArticle(
        url = article.url,
        imageUrl = article.visual.url,
        date = formatTimeStamp(article.timeStamp),
        title = article.title
    )
}