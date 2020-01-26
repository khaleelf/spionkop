package uk.co.khaleelfreeman.spion.repo

import uk.co.khaleelfreeman.spion.service.RefreshState
import uk.co.khaleelfreeman.spion.service.retrofit.dto.Article

interface Repository {
    fun getArticles(): Array<Article>
    fun fetchArticles(callback: () -> Unit)
    fun addFilter(source: String)
    fun removeFilter(source: String)
    fun getSources() : Set<String>
    fun getRefreshState(): RefreshState
    fun teardown()
}