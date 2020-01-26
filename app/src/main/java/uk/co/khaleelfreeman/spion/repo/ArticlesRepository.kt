package uk.co.khaleelfreeman.spion.repo

import io.reactivex.disposables.Disposable
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import uk.co.khaleelfreeman.spion.service.ArticleNetworkService
import uk.co.khaleelfreeman.spion.service.NetworkService
import uk.co.khaleelfreeman.spion.service.RefreshState
import uk.co.khaleelfreeman.spion.service.retrofit.dto.Article
import uk.co.khaleelfreeman.spion.util.mediaSources

class ArticleRepository(private val articleNetworkService: NetworkService = ArticleNetworkService()) :
    Repository {
    private var _refreshState: RefreshState = RefreshState.Fetching
    private var articles: Array<Article> = emptyArray()
    private val thirtyMinInMillis = DateTime(1800000L)
    private var filteredArticles = emptyArray<Article>()
    private lateinit var _sources: Set<String>
    private val articleFilters: MutableMap<String, Array<Article>> = mutableMapOf()
    private lateinit var d: Disposable
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
        if (currentTimeMinusPublished().isAfter(thirtyMinInMillis)) {
            _refreshState = RefreshState.Fetching
            d = articleNetworkService.execute().subscribe { response ->
                published = response.published
                articles = response.articles
                _sources = mediaSources(response.articles)
                //create separate lists from each source
                generateFilteredArticles(_sources, articles)
                _refreshState = RefreshState.Complete
                callback()
            }
        } else {
            _refreshState = RefreshState.Complete
            callback()
        }
    }

    private fun generateFilteredArticles(
        sources: Set<String>,
        articles: Array<Article>
    ) {
        sources.forEach { source: String ->
            val filteredArticle: Array<Article> =
                articles.filter { article -> article.url.contains(source) }.toTypedArray()
            articleFilters[source] = filteredArticle
        }
    }

    override fun addFilter(source: String) {
        filteredArticles += articleFilters[source]!!
    }

    override fun removeFilter(source: String) {
        filteredArticles =
            filteredArticles.filterNot { article -> articleFilters[source]!!.any { it == article } }
                .toTypedArray()
    }

    override fun getSources(): Set<String> = _sources

    override fun getRefreshState(): RefreshState {
        val refreshState = _refreshState
        return refreshState
    }

    private fun currentTimeMinusPublished() = DateTime().toDateTime(DateTimeZone.UTC).minus(
        published
    )

    override fun teardown() {
        d.dispose()
    }
}