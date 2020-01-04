package uk.co.khaleelfreeman.spion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uk.co.khaleelfreeman.spion.repo.Repository
import uk.co.khaleelfreeman.spion.service.Article


class MainActivityViewModel : ViewModel() {
    private val articles by lazy { MutableLiveData<Array<Article>>() }

    fun getArticles(repository: Repository): LiveData<Array<Article>> {
        repository.fetchArticles {
            this.articles.value = repository.getArticles()
        }
        return articles
    }
}