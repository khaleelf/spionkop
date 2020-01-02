package uk.co.khaleelfreeman.spion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainActivityViewModel : ViewModel() {
    private val articles by lazy { MutableLiveData<Array<Article>>() }
    fun getArticles(repository: Repository): LiveData<Array<Article>> {
        repository.fetchArticles {
            this.articles.value = repository.getArticles()
        }
        return articles
    }
}