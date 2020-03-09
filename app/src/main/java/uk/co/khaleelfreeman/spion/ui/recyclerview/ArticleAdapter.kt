package uk.co.khaleelfreeman.spion.ui.recyclerview

import android.net.Uri
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uk.co.khaleelfreeman.spion.R
import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle

class ArticleAdapter(
    var articles: List<SpionkopArticle>,
    private val viewHolderFactory: ViewHolderFactory
) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    class ViewHolder(val articleView: ArticleView) : RecyclerView.ViewHolder(articleView.root)

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ArticleType.FIRST_FEATURED.ordinal
            in 1..2 -> ArticleType.FEATURED.ordinal
            3 -> ArticleType.FIRST_GENERAL.ordinal
            else -> ArticleType.GENERAL.ordinal
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return viewHolderFactory.getViewHolder(viewType, parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setViewClickListenerForCustomTabs(holder, position)
        setTitle(holder, position)
        setDate(articles[position], holder)
        loadImage(holder, position)
    }

    private fun setTitle(
        holder: ViewHolder,
        position: Int
    ) {
        holder.articleView.title.text = articles[position].title
    }

    private fun loadImage(
        holder: ViewHolder,
        position: Int
    ) {
        Glide.with(holder.articleView.root).load(articles[position].imageUrl).into(holder.articleView.image)
    }

    private fun setDate(
        article: SpionkopArticle,
        holder: ViewHolder
    ) {
        holder.articleView.date.text = article.date
    }

    private fun setViewClickListenerForCustomTabs(
        holder: ViewHolder,
        position: Int
    ) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(getColor(holder.articleView.root.context,
            R.color.primaryColor
        ))
        val customTabsIntent = builder.build()
        holder.articleView.root.setOnClickListener {
            customTabsIntent.launchUrl(holder.articleView.root.context, Uri.parse(articles[position].url))
        }
    }

    override fun getItemCount() = articles.size
}