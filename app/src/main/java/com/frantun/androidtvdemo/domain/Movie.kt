package com.frantun.androidtvdemo.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val summary: String,
    val releaseDate: String,
    val poster: String,
    val backdrop: String,
) : Parcelable