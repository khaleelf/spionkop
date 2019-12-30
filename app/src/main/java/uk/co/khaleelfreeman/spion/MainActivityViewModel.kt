package uk.co.khaleelfreeman.spion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainActivityViewModel : ViewModel() {
    private val articles by lazy { MutableLiveData<Array<Article>>() }
    fun getArticles(repository: Repository): LiveData<Array<Article>> {
        repository.fetchArticles {
            val articles = repository.getArticles()
            this.articles.value = articles
        }
        return articles
    }
}