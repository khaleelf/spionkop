package uk.co.khaleelfreeman.spionkoparticledomain.repo

import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle

interface Repository {
    fun getArticles(): Array<SpionkopArticle>
    fun fetchArticles(callback: () -> Unit)
    fun addFilter(source: String)
    fun removeFilter(source: String)
    fun getSources() : Set<String>
    fun getRefreshState(): RefreshState
    fun teardown()
}