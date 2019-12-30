package uk.co.khaleelfreeman.spion

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        val builder =  CustomTabsIntent.Builder()
        builder.setToolbarColor(getColor(holder.views.root.context, R.color.primaryColor))
        val customTabsIntent = builder.build()
        holder.views.title.text = articles[position].title
        val dateWords =
            Date(articles[position].timeStamp.toLong()).toString().split(" ", ignoreCase = true)
        val date = "${dateWords[0]} ${dateWords[1]} ${dateWords[2]}"
        holder.views.date.text = date
        Glide.with(holder.views.root).load(articles[position].visual.url).into(holder.views.image)
        holder.views.root.setOnClickListener {
            customTabsIntent.launchUrl(holder.views.root.context, Uri.parse(articles[position].url))
        }
    }

    override fun getItemCount() = articles.size
}