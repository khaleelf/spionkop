package uk.co.khaleelfreeman.spion.ui.recyclerview

import androidx.recyclerview.widget.DiffUtil
import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle

class ArticleDiffUtilCallback(
    private val oldArticles: Array<SpionkopArticle>,
    private val newArticles: Array<SpionkopArticle>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldArticles.size

    override fun getNewListSize(): Int = newArticles.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldArticles[oldItemPosition] === newArticles[newItemPosition]
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldArticles[oldItemPosition].url === newArticles[newItemPosition].url
    }
}