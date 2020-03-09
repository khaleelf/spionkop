package uk.co.khaleelfreeman.spionkoparticledomain.repo

import io.reactivex.Single
import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle

interface Repository {
    fun getArticles(): List<SpionkopArticle>
    fun fetchArticles(): Single<ViewData>
    fun addFilter(source: String)
    fun removeFilter(source: String)
    fun getSources() : Set<String>
}