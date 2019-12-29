package uk.co.khaleelfreeman.spion

import android.os.Bundle
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
        model.getArticles(ArticleRepository()).observe(this, Observer<Array<Article>> { articles ->
            val viewAdapter = ArticleAdapter(articles, ArticleViewHolder)
            recycler_view.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        })
    }
}