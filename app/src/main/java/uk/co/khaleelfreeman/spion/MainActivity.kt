package uk.co.khaleelfreeman.spion

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val viewAdapter: ArticleAdapter by lazy { ArticleAdapter(emptyArray(), ArticleViewHolder) }
    val viewManager: RecyclerView.LayoutManager by lazy { LinearLayoutManager(this) }
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

        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

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

    override fun onResume() {
        model.getArticles(ArticleRepository).observe(this, Observer<Array<Article>> { articles ->


            if(! Arrays.deepEquals(viewAdapter.articles, articles)) {
                loader.visibility = View.GONE
                recycler_view.visibility = View.VISIBLE

                val diffCallback = ArticleDiffUtilCallback(viewAdapter.articles, articles)
                val diffResult = DiffUtil.calculateDiff(diffCallback)
                viewAdapter.articles = articles
                diffResult.dispatchUpdatesTo(viewAdapter)
            }

        })
        super.onResume()
    }

    private fun showInfo() {
        startActivity(Intent(this, AppInfoActivity::class.java))
    }
}


class ArticleDiffUtilCallback(private val oldArticles: Array<Article>, private val newArticles: Array<Article>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldArticles.size

    override fun getNewListSize(): Int = newArticles.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldArticles[oldItemPosition] === newArticles[newItemPosition]
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldArticles[oldItemPosition].url === newArticles[newItemPosition].url
    }

}