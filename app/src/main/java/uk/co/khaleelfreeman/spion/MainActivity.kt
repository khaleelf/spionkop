package uk.co.khaleelfreeman.spion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewAdapter : ArticleAdapter
    lateinit var viewManager : LinearLayoutManager
    val model by lazy { ViewModelProviders.of(this)[MainActivityViewModel::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.inflateMenu(R.menu.menu_item)

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.info -> {
                    showInfo()
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }

        viewAdapter = ArticleAdapter(emptyArray(), ArticleViewHolder)
        viewManager = LinearLayoutManager(this)
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        model.getArticles(ArticleRepository).observe(this, Observer<Array<Article>> { articles ->
            if (articles.isNotEmpty()) {
                viewAdapter.articles = emptyArray()
                viewAdapter.notifyItemRangeRemoved(0, articles.size)
            }
            loader.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
            viewAdapter.articles = articles
            viewAdapter.notifyItemRangeInserted(0,articles.size)
        })

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    app_bar_layout.elevation = 20f
                } else {
                    app_bar_layout.elevation = 0f
                }
            }
        })

    }

    private fun showInfo() {
        startActivity(Intent(this, AppInfoActivity::class.java))
    }
}