package uk.co.khaleelfreeman.spion

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter(
    private val articles: Array<Article>,
    private val viewHolderFactory: ViewHolderFactory
) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    class ViewHolder(val views: Views) : RecyclerView.ViewHolder(views.root)

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
        holder.views.title.text = articles[position].title
        holder.views.date.text = articles[position].timeStamp
        holder.views.image.setImageDrawable(holder.views.date.context.getDrawable(R.drawable.ic_launcher_foreground))
    }

    override fun getItemCount() = articles.size
}