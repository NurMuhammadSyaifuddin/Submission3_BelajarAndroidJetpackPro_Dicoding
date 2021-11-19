package com.project.lastsubmission.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.lastsubmission.ui.content.favorite.movie.FavoriteMovieFragment
import com.project.lastsubmission.ui.content.favorite.tvshow.FavoriteTvShowFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FavoriteMovieFragment()
            else -> FavoriteTvShowFragment()
        }
}