package uk.co.khaleelfreeman.spion.repo

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import uk.co.khaleelfreeman.spion.service.Article
import uk.co.khaleelfreeman.spion.service.ArticleNetworkService

interface Repository {
    val articlefilters: Map<String, Array<Article>>
    fun getArticles(): Array<Article>
    fun fetchArticles(callback: () -> Unit)
    fun addFilter(source: String)
    fun removeFilter(source: String)
    var sources: Set<String>
}

class ArticleRepository(private val articleNetworkService: ArticleNetworkService = ArticleNetworkService()) :
    Repository {

    private val THIRTY_MIN_IN_MILLIS = DateTime(1800000L)
    override var sources: Set<String> = mutableSetOf()
    private var articles: Array<Article> = emptyArray()
    override val articlefilters: MutableMap<String, Array<Article>> = mutableMapOf()
    var filteredArticles = emptyArray<Article>()
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
                sources = mediaSources(response.articles)
                //create seperate lists from each source
                generateFilteredArticles(sources, articles)
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


    private fun currentTimeMinusPublished() = DateTime().toDateTime(DateTimeZone.UTC).minus(
        published
    )
}

fun mediaSources(articles: Array<Article>): Set<String> {
    return articles.map { it.url }.map {
        val indexOfFirstSeparator = it.indexOf('.')
        val subString = it.substring(indexOfFirstSeparator + 1, it.length)
        val indexOfSecondSeparator = subString.indexOf('.')
        it.substring(indexOfFirstSeparator + 1..indexOfFirstSeparator + indexOfSecondSeparator)
    }.toSet()
}