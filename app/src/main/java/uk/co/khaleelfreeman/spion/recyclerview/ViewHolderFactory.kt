package uk.co.khaleelfreeman.spion.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import uk.co.khaleelfreeman.spion.R
import uk.co.khaleelfreeman.spion.recyclerview.ArticleAdapter.ViewHolder

interface ViewHolderFactory {
    fun getViewHolder(viewType: Int, parent: ViewGroup): ViewHolder
}

object ArticleViewHolder :
    ViewHolderFactory {
    override fun getViewHolder(viewType: Int, parent: ViewGroup): ViewHolder {
        val article =
            getLayout(
                viewType,
                parent
            )

        return when (viewType) {
            ArticleType.FIRST_FEATURED.ordinal -> ViewHolder(
                Views(
                    article.findViewById(R.id.featured_article_title),
                    article.findViewById(R.id.featured_article_date),
                    article.findViewById(R.id.featured_article_image),
                    article
                )
            )
            ArticleType.FEATURED.ordinal -> ViewHolder(
                Views(
                    article.findViewById(R.id.featured_article_title),
                    article.findViewById(R.id.featured_article_date),
                    article.findViewById(R.id.featured_article_image),
                    article
                )
            )
            ArticleType.FIRST_GENERAL.ordinal -> ViewHolder(
                Views(
                    article.findViewById(R.id.general_article_title),
                    article.findViewById(R.id.general_article_time_stamp),
                    article.findViewById(R.id.general_article_image),
                    article
                )
            )
            else -> ViewHolder(
                Views(
                    article.findViewById(R.id.general_article_title),
                    article.findViewById(R.id.general_article_time_stamp),
                    article.findViewById(R.id.general_article_image),
                    article
                )
            )
        }
    }

    private fun getLayout(viewType: Int, parent: ViewGroup): ViewGroup {
        return when (viewType) {
            ArticleType.FIRST_FEATURED.ordinal -> LayoutInflater.from(parent.context).inflate(
                R.layout.first_featured_layout,
                parent,
                false
            ) as LinearLayout
            ArticleType.FEATURED.ordinal -> LayoutInflater.from(parent.context).inflate(
                R.layout.featured_article_layout,
                parent,
                false
            ) as CardView
            ArticleType.FIRST_GENERAL.ordinal -> LayoutInflater.from(parent.context).inflate(
                R.layout.first_general_article,
                parent,
                false
            ) as LinearLayout
            else -> LayoutInflater.from(parent.context).inflate(
                R.layout.general_article_layout,
                parent,
                false
            ) as CardView
        }
    }
}

data class Views(
    val title: TextView,
    val date: TextView,
    val image: ImageView,
    val root: ViewGroup
)

enum class ArticleType {
    FIRST_FEATURED,
    FEATURED,
    FIRST_GENERAL,
    GENERAL,
}