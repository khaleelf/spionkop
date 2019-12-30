package uk.co.khaleelfreeman.spion

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)
        val model = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        val viewAdapter = ArticleAdapter(emptyArray(), ArticleViewHolder)
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        model.getArticles(ArticleRepository()).observe(this, Observer<Array<Article>> { articles ->
            loader.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
            viewAdapter.articles = articles
            viewAdapter.notifyItemRangeInserted(0,articles.size)
        })
    }
}