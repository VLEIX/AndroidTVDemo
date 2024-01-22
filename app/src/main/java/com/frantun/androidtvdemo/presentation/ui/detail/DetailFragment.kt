package com.frantun.androidtvdemo.presentation.ui.detail

import android.os.Bundle
import android.view.View
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import com.frantun.androidtvdemo.domain.Movie
import com.frantun.androidtvdemo.presentation.extensions.loadImageUrl
import com.frantun.androidtvdemo.presentation.ui.detail.DetailActivity.Companion.MOVIE_EXTRA
import com.frantun.androidtvdemo.presentation.ui.views.DetailBackgroundState
import com.frantun.androidtvdemo.presentation.ui.views.FullViewDetailsMovieRowPresenter

class DetailFragment : DetailsSupportFragment() {

    private val detailsBackgroundState = DetailBackgroundState(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = requireActivity().intent.getParcelableExtra<Movie>(MOVIE_EXTRA)
            ?: throw IllegalStateException("Movie not found.")

        val presenter = FullViewDetailsMovieRowPresenter(requireActivity())

        val rowsAdapter = ArrayObjectAdapter(presenter)
        val detailsOverviewRow = DetailsOverviewRow(movie).apply {
            loadImageUrl(requireContext(), movie.poster)
            actionsAdapter = presenter.buildActions(presenter)
        }

        rowsAdapter.add(detailsOverviewRow)

        adapter = rowsAdapter

        detailsBackgroundState.loadUrl(movie.backdrop)
    }
}
