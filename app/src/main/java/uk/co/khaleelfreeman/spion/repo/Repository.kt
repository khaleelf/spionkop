package uk.co.khaleelfreeman.spion.repo

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import uk.co.khaleelfreeman.spion.service.Article
import uk.co.khaleelfreeman.spion.service.ArticleNetworkService
import uk.co.khaleelfreeman.spion.util.mediaSources

interface Repository {
    val articlefilters: Map<String, Array<Article>>
    fun getArticles(): Array<Article>
    fun fetchArticles(callback: () -> Unit)
    fun addFilter(source: String)
    fun removeFilter(source: String)
    var sources: Set<String>
}