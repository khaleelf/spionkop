package uk.co.khaleelfreeman.spion.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle
import uk.co.khaleelfreeman.spionkoparticledomain.repo.RefreshState
import uk.co.khaleelfreeman.spionkoparticledomain.repo.Repository


class MainActivityViewModel : ViewModel() {
    private val _refreshState by lazy { MutableLiveData<RefreshState>() }
    val refreshState: LiveData<RefreshState> = _refreshState
    private val _articles by lazy { MutableLiveData<Array<SpionkopArticle>>() }
    val articles: LiveData<Array<SpionkopArticle>> = _articles
    private val _sources by lazy { MutableLiveData<Set<String>>() }
    val sources: LiveData<Set<String>> = _sources
    private lateinit var repository: Repository

    fun fetchArticles() {
        repository.fetchArticles {
            _articles.value = repository.getArticles()
            _sources.value = repository.getSources()
            _refreshState.value = repository.getRefreshState()
        }
    }

    fun setRepository(repository: Repository) {
        this.repository = repository
    }

    fun addFilter(source: String) {
        repository.addFilter(source)
        _articles.value = repository.getArticles()
    }

    fun removeFilter(source: String) {
        repository.removeFilter(source)
        _articles.value = repository.getArticles()
    }

    override fun onCleared() {
        repository.teardown()
    }
}