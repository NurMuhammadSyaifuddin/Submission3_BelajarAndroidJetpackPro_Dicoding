package com.project.lastsubmission.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.project.lastsubmission.R

const val BASE_URL_API_IMAGE = "https://image.tmdb.org/t/p/"
const val POSTER_SIZE_W185 = "w185"
const val POSTER_SIZE_W780 = "w780"

fun ImageView.loadImageMovie(url: Any?){
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading_movie))
        .into(this)
}

fun ImageView.loadImageTvShow(url: Any?){
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading_tvshow))
        .into(this)
}

fun ProgressBar.showLoading(state: Boolean){
        if (state) visible() else gone()
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun Context.showToastConnectionLost(){
    Toast.makeText(
        this,
        "Your connection lost",
        Toast.LENGTH_SHORT
    ).show()
}

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun showEmptyFavorite(icon: ImageView, title: TextView, desc: TextView){
    icon.visible()
    title.visible()
    desc.visible()
}

fun hideEmptyFavorite(icon: ImageView, title: TextView, desc: TextView){
    icon.gone()
    title.gone()
    desc.gone()
}