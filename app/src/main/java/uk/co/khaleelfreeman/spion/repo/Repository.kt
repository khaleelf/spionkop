package uk.co.khaleelfreeman.spion.repo

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import uk.co.khaleelfreeman.spion.service.Article
import uk.co.khaleelfreeman.spion.service.ArticleNetworkService

interface Repository {
    fun getArticles(): Array<Article>
    fun fetchArticles(callback: () -> Unit)
}

class ArticleRepository(val articleNetworkService: ArticleNetworkService = ArticleNetworkService()) :
    Repository {

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
            articleNetworkService.execute { response ->
                published = response.published
                articles = response.articles
                callback()
            }
        } else {
            callback()
        }
    }

    private fun currentTimeMinusPublished() = DateTime().toDateTime(DateTimeZone.UTC).minus(
        published
    )
}