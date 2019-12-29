package uk.co.khaleelfreeman.spion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)
        val viewAdapter = ArticleAdapter(dummyData, ArticleViewHolder)

        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}


val dummyData = arrayOf(
    Article("Example Title could include anything here just make it a little longer", timeStamp = "12/12/2019"),
    Article("Example Title could include anything here", timeStamp = "12/12/2019"),
    Article("Example Title could include anything here", timeStamp = "12/12/2019"),
    Article("Example Title could include anything here", timeStamp = "12/12/2019"),
    Article("Example Title could include anything here", timeStamp = "12/12/2019"),
    Article("Example Title could include anything here", timeStamp = "12/12/2019"),
    Article("Example Title could include anything here", timeStamp = "12/12/2019"),
    Article("Example Title could include anything here", timeStamp = "12/12/2019"),
    Article("Example Title could include anything here", timeStamp = "12/12/2019"),
    Article("Example Title could include anything here", timeStamp = "12/12/2019"),
    Article("Example Title could include anything here", timeStamp = "12/12/2019"),
    Article("Example Title could include anything here", timeStamp = "12/12/2019"),
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