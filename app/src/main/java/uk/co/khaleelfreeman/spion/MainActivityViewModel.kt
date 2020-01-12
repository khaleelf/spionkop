package uk.co.khaleelfreeman.spion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uk.co.khaleelfreeman.spion.repo.Repository
import uk.co.khaleelfreeman.spion.service.Article


class MainActivityViewModel : ViewModel() {
    private val _articles by lazy { MutableLiveData<Array<Article>>() }
    val articles: LiveData<Array<Article>> = _articles
    private val _sources by lazy { MutableLiveData<Set<String>>() }
    val sources: LiveData<Set<String>> = _sources
    private lateinit var repository: Repository

    fun fetchArticles() {
        repository.fetchArticles {
            val repoArticles = repository.getArticles()
            val repoSources = repository.getSources()
            if (_articles.value == null) {
                _articles.value = repoArticles
                _sources.value = repoSources
            } else {
                if (!_articles.value!!.contentDeepEquals(repoArticles)){
                    _articles.value = repoArticles
                    _sources.value = repoSources
                }
            }
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
}