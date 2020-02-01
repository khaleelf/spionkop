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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import uk.co.khaleelfreeman.spion.R
import uk.co.khaleelfreeman.spion.ui.recyclerview.ArticleAdapter
import uk.co.khaleelfreeman.spion.ui.recyclerview.ArticleDiffUtilCallback
import uk.co.khaleelfreeman.spion.ui.recyclerview.ArticleViewHolderFactory
import uk.co.khaleelfreeman.spionkoparticledomain.SpionkopArticle
import uk.co.khaleelfreeman.spionkoparticledomain.repo.RefreshState


class MainActivity : AppCompatActivity() {

    private val viewAdapter: ArticleAdapter by lazy {
        ArticleAdapter(emptyArray(), ArticleViewHolderFactory)
    }
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.inflateMenu(R.menu.menu_item)

        setupToolbar()
        setupRecyclerView()
        setupSwipeToRefresh()



        viewModel.fetchArticles()
        viewModel.articles.observe(this, Observer<Array<SpionkopArticle>> { articles ->
            if (viewModel.onFirstLaunch) fadeOutLoadingAnimation()
            val diffCallback = ArticleDiffUtilCallback(viewAdapter.articles, articles)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            viewAdapter.articles = articles
            diffResult.dispatchUpdatesTo(viewAdapter)
        })

        viewModel.sources.observe(this, Observer<Set<String>> { sources ->
            sources_container.removeAllViews()
            sources.forEach { source -> setupMediaSource(source) }
        })

        viewModel.refreshState.observe(this, Observer<RefreshState> { state ->
            if (state == RefreshState.Complete) {
                swipe_container.isRefreshing = false
            }
        })

    }

    private fun setupSwipeToRefresh() {
        swipe_container.setOnRefreshListener {
            viewModel.fetchArticles()
        }
        swipe_container.setColorSchemeResources(R.color.secondaryDarkColor)
    }

    private fun setupRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        elevateToolbarOnScroll()
    }

    private fun elevateToolbarOnScroll() {
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

    private fun showInfo() {
        startActivity(Intent(this, AppInfoActivity::class.java))
    }

    private fun fadeOutLoadingAnimation() {
        val fadeIn = fade(0f, 1f)
        val fadeOut = fade(1f, 0f)
        AnimatorSet().apply {
            play(fadeOut(loader)).before(fadeIn(recycler_view)).before(fadeIn(sources_container))
            start()
        }
    }

    private fun fade(from: Float, to: Float): (view: View) -> Animator {
        return { v ->
            ObjectAnimator.ofFloat(v, "alpha", from, to).apply {
                duration = 1000
            }
        }
    }

    private fun setupMediaSource(source: String) {
        val chip: Chip =
            LayoutInflater.from(this).inflate(R.layout.media_source_chip, null) as Chip
        sources_container.addView(chip.apply {
            text = "#$source"
        })

        chip.setOnClickListener {
            filterSource(chip, source)
        }
    }

    private fun filterSource(chip: Chip, source: String) {
        if (chip.isChecked) {
            viewModel.addFilter(source)
        } else {
            viewModel.removeFilter(source)
        }
    }
}
