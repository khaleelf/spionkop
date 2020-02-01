package uk.co.khaleelfreeman.spion.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uk.co.khaleelfreeman.spion.ui.util.LaunchRefreshDelegate
import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle
import uk.co.khaleelfreeman.spionkoparticledomain.repo.RefreshState
import uk.co.khaleelfreeman.spionkoparticledomain.repo.Repository


class MainActivityViewModel(private val repository: Repository) : ViewModel() {
    private val _refreshState by lazy { MutableLiveData<RefreshState>() }
    private val _articles by lazy { MutableLiveData<Array<SpionkopArticle>>() }
    private val _sources by lazy { MutableLiveData<Set<String>>() }
    val onFirstLaunch: Boolean by LaunchRefreshDelegate()
    val refreshState: LiveData<RefreshState> = _refreshState
    val articles: LiveData<Array<SpionkopArticle>> = _articles
    val sources: LiveData<Set<String>> = _sources

    fun fetchArticles() {
        _refreshState.value = RefreshState.Fetching
        repository.fetchArticles {
            _articles.value = repository.getArticles()
            _sources.value = repository.getSources()
            _refreshState.value = RefreshState.Complete
        }
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