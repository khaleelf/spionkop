package uk.co.khaleelfreeman.spion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)
        val viewAdapter = ArticleAdapter(dummyData)

        val recyclerView = recycler_view.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }
}

class ArticleAdapter(private val myDataset: Array<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ViewHolder(val title: TextView, val date: TextView, val image: ImageView, root: FrameLayout) : RecyclerView.ViewHolder(root)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // create a new view
        val generalArticle = LayoutInflater.from(parent.context)
            .inflate(R.layout.general_article_layout, parent, false) as FrameLayout
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(
            generalArticle.findViewById(R.id.general_article_title),
            generalArticle.findViewById(R.id.general_article_time_stamp),
            generalArticle.findViewById(R.id.general_article_image),
            generalArticle)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.title.text = myDataset[position].title
        holder.date.text = myDataset[position].timeStamp
        holder.image.setImageDrawable(holder.date.context.getDrawable(R.drawable.ic_launcher_foreground))
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}

val dummyData = arrayOf(
    Article("title 1"),
    Article("title 2"),
    Article("title 3"),
    Article("title 4"),
    Article("title 5")
)

data class Article(
    val title: String = "",
    val image: String = "",
    val timeStamp: String = "",
    val url: String = ""
)
