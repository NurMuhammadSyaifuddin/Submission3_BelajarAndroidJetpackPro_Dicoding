package com.project.lastsubmission.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.lastsubmission.data.source.CatalogRepository
import com.project.lastsubmission.data.source.local.entitiy.MovieEntitiy
import com.project.lastsubmission.data.source.local.entitiy.TvShowEntity
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val catalogRepository: CatalogRepository): ViewModel() {

    fun getDetailMovie(movieId: Int): LiveData<MovieEntitiy> = catalogRepository.getDetailMovie(movieId)

    fun getDetailTvShow(tvId: Int): LiveData<TvShowEntity> = catalogRepository.getDetailTvShow(tvId)

    fun setFavoriteMovie(movie: MovieEntitiy){
        catalogRepository.setFavoriteMovie(movie)
    }

    fun setFavoriteTvShow(tvshow: TvShowEntity){
        catalogRepository.setFavoriteTvShow(tvshow)
    }

}