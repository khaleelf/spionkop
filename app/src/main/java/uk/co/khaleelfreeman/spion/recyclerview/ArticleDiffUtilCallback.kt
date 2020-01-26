package uk.co.khaleelfreeman.spion.recyclerview

import androidx.recyclerview.widget.DiffUtil
import uk.co.khaleelfreeman.spion.service.retrofit.dto.Article

class ArticleDiffUtilCallback(private val oldArticles: Array<Article>, private val newArticles: Array<Article>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldArticles.size

    override fun getNewListSize(): Int = newArticles.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldArticles[oldItemPosition] === newArticles[newItemPosition]
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldArticles[oldItemPosition].url === newArticles[newItemPosition].url
    }
}