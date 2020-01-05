package uk.co.khaleelfreeman.spion

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.khaleelfreeman.spion.recyclerview.ArticleAdapter
import uk.co.khaleelfreeman.spion.recyclerview.ArticleDiffUtilCallback
import uk.co.khaleelfreeman.spion.recyclerview.ArticleViewHolder
import uk.co.khaleelfreeman.spion.repo.ArticleRepository
import uk.co.khaleelfreeman.spion.service.Article
import java.util.*

class MainActivity : AppCompatActivity() {

    private val viewAdapter: ArticleAdapter by lazy {
        ArticleAdapter(
            emptyArray(),
            ArticleViewHolder
        )
    }
    private val viewManager: RecyclerView.LayoutManager by lazy { LinearLayoutManager(this) }
    private val model by lazy { ViewModelProviders.of(this)[MainActivityViewModel::class.java] }
    private var mediaSourceSet = false

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
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
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
        model.getArticles(ArticleRepository()).observe(this, Observer<Array<Article>> { articles ->
            
            if(! Arrays.deepEquals(viewAdapter.articles, articles)) {
                val loaderFadeAnim = ObjectAnimator.ofFloat(loader, "alpha", 1f, 0f).apply {
                    duration = 1000
                }
                val recyclerFadeAnim = ObjectAnimator.ofFloat(recycler_view, "alpha", 0f, 1f).apply {
                    duration = 1000
                }
                AnimatorSet().apply {
                    play(loaderFadeAnim).before(recyclerFadeAnim)
                    start()
                }
                val diffCallback = ArticleDiffUtilCallback(viewAdapter.articles, articles)
                val diffResult = DiffUtil.calculateDiff(diffCallback)
                viewAdapter.articles = articles
                diffResult.dispatchUpdatesTo(viewAdapter)

                mediaSources(articles).forEach { inflateMediaSources(it) }


            }

        })
        super.onResume()
    }

    private fun inflateMediaSources(source: String): Chip {
        val chip: Chip =
            LayoutInflater.from(this).inflate(R.layout.media_source_chip, null) as Chip
        chip_container.addView(chip.apply {
            text = "#$source"
        })
        return chip
    }

    private fun showInfo() {
        startActivity(Intent(this, AppInfoActivity::class.java))
    }
}

fun mediaSources(articles: Array<Article>): Set<String> {
    return articles.map { it.url }.map {
        val indexOfFirstSeparator = it.indexOf('.')
        val subString = it.substring(indexOfFirstSeparator + 1, it.length)
        val indexOfSecondSeparator = subString.indexOf('.')
        it.substring(indexOfFirstSeparator + 1..indexOfFirstSeparator + indexOfSecondSeparator)
    }.toSet()
}
