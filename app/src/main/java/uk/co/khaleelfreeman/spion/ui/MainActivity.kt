package uk.co.khaleelfreeman.spion.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import uk.co.khaleelfreeman.spion.R
import uk.co.khaleelfreeman.spion.recyclerview.ArticleAdapter
import uk.co.khaleelfreeman.spion.recyclerview.ArticleDiffUtilCallback
import uk.co.khaleelfreeman.spion.recyclerview.ArticleViewHolderFactory
import uk.co.khaleelfreeman.spion.repo.Repository
import co.uk.khaleelfreeman.service.RefreshState
import co.uk.khaleelfreeman.service.retrofit.dto.Article


class MainActivity : AppCompatActivity() {

    private val viewAdapter: ArticleAdapter by lazy {
        ArticleAdapter(emptyArray(), ArticleViewHolderFactory)
    }
    private val viewManager: RecyclerView.LayoutManager by lazy { LinearLayoutManager(this) }
    private val model by lazy { ViewModelProvider(this)[MainActivityViewModel::class.java] }
    private var firstLaunch = true
    val repo : Repository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.inflateMenu(R.menu.menu_item)

        setupToolbar()
        setupRecyclerView()
        setupSwipeToRefresh()
        model.setRepository(repo)
    }

    private fun setupSwipeToRefresh() {
        swipe_container.setOnRefreshListener {
            model.fetchArticles()
        }

        swipe_container.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }

    private fun setupRecyclerView() {
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

    private fun setupToolbar() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.info -> {
                    showInfo()
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }
    }

    override fun onResume() {
        model.fetchArticles()
        model.articles.observe(this, Observer<Array<Article>> { articles ->
                if (firstLaunch) {
                    fadeOutLoadingAnimation()
                    firstLaunch = false
                }
                val diffCallback = ArticleDiffUtilCallback(viewAdapter.articles, articles)
                val diffResult = DiffUtil.calculateDiff(diffCallback)
                viewAdapter.articles = articles
                diffResult.dispatchUpdatesTo(viewAdapter)
        })

        model.sources.observe(this, Observer<Set<String>> { sources ->
            chip_container.removeAllViews()
            sources.forEach { source ->
                val chip = inflateMediaSources(source)
                chip.setOnClickListener {
                    if (chip.isChecked) {
                        model.addFilter(source)
                    } else {
                        model.removeFilter(source)
                    }
                }
            }
        })

        model.refreshState.observe(this, Observer<RefreshState> { state ->
            if (state == RefreshState.Complete) {
                swipe_container.isRefreshing = false
            }
        })
        super.onResume()
    }

    private fun fadeOutLoadingAnimation() {
        val fadeIn = fade(0f, 1f)
        val fadeOut = fade(1f, 0f)

        AnimatorSet().apply {
            play(fadeOut(loader)).before(fadeIn(recycler_view)).before(fadeIn(chip_container))
            start()
        }
    }

    private fun fade(from: Float, to: Float): (view : View) -> Animator {
        return { v->
            ObjectAnimator.ofFloat(v, "alpha", from, to).apply {
                duration = 1000
            }
        }
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
