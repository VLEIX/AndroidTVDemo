package com.frantun.androidtvdemo.presentation.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.lifecycle.lifecycleScope
import com.frantun.androidtvdemo.R
import com.frantun.androidtvdemo.data.remote.MoviesRepository
import com.frantun.androidtvdemo.domain.Movie
import com.frantun.androidtvdemo.presentation.ui.detail.DetailActivity
import com.frantun.androidtvdemo.presentation.ui.detail.DetailActivity.Companion.MOVIE_EXTRA
import com.frantun.androidtvdemo.presentation.ui.detail.DetailActivity.Companion.SHARED_ELEMENT_EXTRA
import com.frantun.androidtvdemo.presentation.ui.views.BackgroundState
import com.frantun.androidtvdemo.presentation.ui.views.CardPresenter
import kotlinx.coroutines.launch

class HomeFragment : BrowseSupportFragment() {

    private lateinit var moviesRepository: MoviesRepository
    private val backgroundState = BackgroundState(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesRepository = MoviesRepository(getString(R.string.api_key_themoviedb))

        title = getString(R.string.browse)

        viewLifecycleOwner.lifecycleScope.launch {
            adapter = buildAdapter()
        }

        setOnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->
            val mainImage = (itemViewHolder.view as? ImageCardView)?.mainImageView
                ?: throw IllegalStateException("mainImageView cannot be retrieved")
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                mainImage,
                SHARED_ELEMENT_EXTRA,
            ).toBundle()

            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(MOVIE_EXTRA, item as Movie)
            }
            startActivity(intent, bundle)
        }

        setOnItemViewSelectedListener { itemViewHolder, item, rowViewHolder, row ->
            (item as? Movie)?.let { backgroundState.loadUrl(it.backdrop) }
        }
    }

    private suspend fun buildAdapter(): ArrayObjectAdapter {
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val cardPresenter = CardPresenter()
        moviesRepository.getCategories().forEach { (category, movies) ->
            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            listRowAdapter.addAll(0, movies)

            val header = HeaderItem(category.ordinal.toLong(), category.name)
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }

        return rowsAdapter
    }
}
