package com.frantun.androidtvdemo.presentation.ui.views

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import androidx.leanback.widget.FullWidthDetailsOverviewSharedElementHelper
import com.frantun.androidtvdemo.R
import com.frantun.androidtvdemo.domain.Movie
import com.frantun.androidtvdemo.presentation.ui.detail.DetailActivity

class FullViewDetailsMovieRowPresenter(private val activity: Activity) :
    FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter()) {

    private enum class Options(@StringRes val stringRes: Int) {
        WATCH_TRAILER(R.string.watch_later),
        FAVORITE(R.string.favorite),
    }

    init {
        val sharedElementHelper = FullWidthDetailsOverviewSharedElementHelper().apply {
            setSharedElementEnterTransition(
                activity,
                DetailActivity.SHARED_ELEMENT_EXTRA
            )
        }

        setListener(sharedElementHelper)
        isParticipatingEntranceTransition = true
    }

    fun buildActions(presenter: FullWidthDetailsOverviewRowPresenter): ArrayObjectAdapter {

        presenter.setOnActionClickedListener { action ->
            val option = Options.entries.first { it.ordinal == action.id.toInt() }
            Toast.makeText(activity, option.stringRes, Toast.LENGTH_SHORT).show()
        }

        return ArrayObjectAdapter().apply {
            Options.entries.forEach { option ->
                add(Action(option.ordinal.toLong(), activity.getString(option.stringRes)))
            }
        }
    }

}

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {
    override fun onBindDescription(vh: ViewHolder, item: Any) {
        val movie = item as Movie
        vh.title.text = movie.title
        vh.subtitle.text = movie.releaseDate
        vh.body.text = movie.summary
    }
}