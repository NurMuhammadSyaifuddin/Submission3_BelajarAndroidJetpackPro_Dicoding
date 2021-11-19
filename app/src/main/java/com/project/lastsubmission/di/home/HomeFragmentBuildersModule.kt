package com.project.lastsubmission.di.home

import com.project.lastsubmission.ui.content.favorite.FavoriteFragment
import com.project.lastsubmission.ui.content.favorite.movie.FavoriteMovieFragment
import com.project.lastsubmission.ui.content.favorite.tvshow.FavoriteTvShowFragment
import com.project.lastsubmission.ui.content.movie.MovieFragment
import com.project.lastsubmission.ui.content.tvshow.TvShowFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentBuildersModule {


    @ContributesAndroidInjector
    abstract fun contributeMovieFragment(): MovieFragment

    @ContributesAndroidInjector
    abstract fun contributeTvShowFragment(): TvShowFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteFragment(): FavoriteFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteMovieFragment(): FavoriteMovieFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteTvShowFragment(): FavoriteTvShowFragment

}