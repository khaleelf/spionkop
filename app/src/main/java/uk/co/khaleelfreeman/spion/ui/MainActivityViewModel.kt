package uk.co.khaleelfreeman.spion.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import uk.co.khaleelfreeman.spion.ui.util.LaunchRefreshDelegate
import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle
import uk.co.khaleelfreeman.spionkoparticledomain.repo.RefreshState
import uk.co.khaleelfreeman.spionkoparticledomain.repo.Repository


class MainActivityViewModel(private val repository: Repository) : ViewModel() {
    private val _refreshState by lazy { MutableLiveData<RefreshState>() }
    private val _articles by lazy { MutableLiveData<List<SpionkopArticle>>() }
    private val _sources by lazy { MutableLiveData<Set<String>>() }
    private var disposable : Disposable? = null
    val onFirstLaunch: Boolean by LaunchRefreshDelegate()
    val refreshState: LiveData<RefreshState> = _refreshState
    val articles: LiveData<List<SpionkopArticle>> = _articles
    val sources: LiveData<Set<String>> = _sources

    fun getViewData() {
        _refreshState.value = RefreshState.Fetching
        disposable = repository.fetchArticles().subscribe {
            viewData -> _articles.value = viewData.articles
            _sources.value = viewData.sources
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
        disposable?.dispose()
    }
}