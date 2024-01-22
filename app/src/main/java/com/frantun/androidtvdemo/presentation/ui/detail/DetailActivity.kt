package com.frantun.androidtvdemo.presentation.ui.detail

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.frantun.androidtvdemo.R

class DetailActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    companion object {
        const val MOVIE_EXTRA = "extra:movie"
        const val SHARED_ELEMENT_EXTRA = "extra:shared_element"
    }
}
