package uk.co.khaleelfreeman.spionkoparticledomain.repo

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle
import uk.co.khaleelfreeman.spionkoparticledomain.service.NetworkService
import uk.co.khaleelfreeman.spionkoparticledomain.util.mediaSources

class ArticleRepository(private val articleNetworkService: NetworkService) : Repository {
    private var articles: List<SpionkopArticle> = emptyList()
    private var filteredArticles = emptyList<SpionkopArticle>()
    private var _sources: Set<String> = emptySet()
    private val articleFilters: MutableMap<String, List<SpionkopArticle>> = mutableMapOf()
    var published: Long = 0L
        private set

    override fun getArticles(): List<SpionkopArticle> {
        return if (filteredArticles.isEmpty()) {
            articles
        } else {
            filteredArticles
        }
    }

    override fun fetchArticles(): Single<ViewData> {
        return articleNetworkService.execute().map { response ->
            val (published, articles) = response
            this.published = published
            this.articles = articles
            _sources = mediaSources(articles)
            //create separate lists from each source
            generateFilteredArticles(_sources, articles)
            ViewData(getArticles(),getSources())
        }
    }

    private fun generateFilteredArticles(
        sources: Set<String>,
        articles: List<SpionkopArticle>
    ) {
        sources.forEach { source: String ->
            val filteredArticle= articles.filter { article -> article.url.contains(source) }
            articleFilters[source] = filteredArticle
        }
    }

    override fun addFilter(source: String) {
        filteredArticles += articleFilters[source]!!
    }

    override fun removeFilter(source: String) {
        filteredArticles =
            filteredArticles.filterNot { article -> articleFilters[source]!!.any { it == article } }
    }

    override fun getSources(): Set<String> = _sources
}

data class ViewData (val articles : List<SpionkopArticle>, val sources : Set<String>)