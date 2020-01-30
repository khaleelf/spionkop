package uk.co.khaleelfreeman.spion.repo

import uk.co.khaleelfreeman.service.RefreshState
import uk.co.khaleelfreeman.service.domain.SpionkopArticle

interface Repository {
    fun getArticles(): Array<SpionkopArticle>
    fun fetchArticles(callback: () -> Unit)
    fun addFilter(source: String)
    fun removeFilter(source: String)
    fun getSources() : Set<String>
    fun getRefreshState(): RefreshState
    fun teardown()
}