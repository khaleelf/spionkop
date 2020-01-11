package uk.co.khaleelfreeman.spion.repo

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import uk.co.khaleelfreeman.spion.service.Article
import uk.co.khaleelfreeman.spion.service.ArticleNetworkService
import uk.co.khaleelfreeman.spion.service.NetworkService
import uk.co.khaleelfreeman.spion.util.mediaSources

class ArticleRepository(private val articleNetworkService: NetworkService = ArticleNetworkService()) :
    Repository {

    private var articles: Array<Article> = emptyArray()
    private val THIRTY_MIN_IN_MILLIS = DateTime(1800000L)
    private var filteredArticles = emptyArray<Article>()
    private lateinit var _sources: Set<String>
    private val articlefilters: MutableMap<String, Array<Article>> = mutableMapOf()
    var published: Long = 0L
        private set(value) {
            field = value
        }

    override fun getArticles(): Array<Article> {
        return if (filteredArticles.isEmpty()) {
            articles.clone()
        } else {
            filteredArticles.clone()
        }
    }

    override fun fetchArticles(callback: () -> Unit) {
        if (currentTimeMinusPublished() > THIRTY_MIN_IN_MILLIS) {
            articleNetworkService.execute { response ->
                published = response.published
                articles = response.articles
                _sources = mediaSources(response.articles)
                //create seperate lists from each source
                generateFilteredArticles(_sources, articles)
                callback()
            }
        } else {
            callback()
        }
    }

    private fun generateFilteredArticles(
        sources: Set<String>,
        articles: Array<Article>
    ) {
        sources.forEach { source : String ->
            val filteredArticle : Array<Article> = articles.filter { article -> article.url.contains(source)}.toTypedArray()
            articlefilters[source] = filteredArticle
        }
    }

    override fun addFilter(source: String) {
        filteredArticles += articlefilters[source]!!
    }

    override fun removeFilter(source: String) {
        filteredArticles = filteredArticles.filterNot { article -> articlefilters[source]!!.any { it == article } }.toTypedArray()
    }

    override fun getSources(): Set<String> = _sources

    private fun currentTimeMinusPublished() = DateTime().toDateTime(DateTimeZone.UTC).minus(
        published
    )
}