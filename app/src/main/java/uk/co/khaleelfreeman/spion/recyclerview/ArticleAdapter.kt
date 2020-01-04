package uk.co.khaleelfreeman.spion.recyclerview

import android.net.Uri
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uk.co.khaleelfreeman.spion.*
import java.util.*

class ArticleAdapter(
    var articles: Array<Article>,
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
        setViewClickListenerForCustomTabs(holder, position)
        setTitle(holder, position)
        setDate(position, holder)
        loadImage(holder, position)
    }

    private fun setTitle(
        holder: ViewHolder,
        position: Int
    ) {
        holder.views.title.text = articles[position].title
    }

    private fun loadImage(
        holder: ViewHolder,
        position: Int
    ) {
        Glide.with(holder.views.root).load(articles[position].visual.url).into(holder.views.image)
    }

    private fun setDate(
        position: Int,
        holder: ViewHolder
    ) {
        val date = formatDate(position)
        holder.views.date.text = date
    }

    // TODO: Move to a util class
    private fun formatDate(position: Int): String {
        val dateWords =
            Date(articles[position].timeStamp.toLong()).toString().split(" ", ignoreCase = true)
        return "${dateWords[0]} ${dateWords[1]} ${dateWords[2]}"
    }

    private fun setViewClickListenerForCustomTabs(
        holder: ViewHolder,
        position: Int
    ) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(getColor(holder.views.root.context,
            R.color.primaryColor
        ))
        val customTabsIntent = builder.build()
        holder.views.root.setOnClickListener {
            customTabsIntent.launchUrl(holder.views.root.context, Uri.parse(articles[position].url))
        }
    }

    override fun getItemCount() = articles.size
}