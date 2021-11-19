package com.project.lastsubmission.data.source.local

import androidx.lifecycle.LiveData
import com.project.lastsubmission.data.source.local.room.CatalogDao
import javax.inject.Inject
import androidx.paging.DataSource
import com.project.lastsubmission.data.source.local.entitiy.MovieEntitiy
import com.project.lastsubmission.data.source.local.entitiy.TvShowEntity

class LocalDataSource @Inject constructor(private val catalogDao: CatalogDao) {

    fun getMovies(): DataSource.Factory<Int, MovieEntitiy> = catalogDao.getMovies()

    fun getTvShow(): DataSource.Factory<Int, TvShowEntity> = catalogDao.getTvShow()

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntitiy> = catalogDao.getFavoriteMovies()

    fun getFavoriteTvShow(): DataSource.Factory<Int, TvShowEntity> = catalogDao.getFavoriteTvShow()

    fun getDetailMovie(movieId: Int): LiveData<MovieEntitiy> =
        catalogDao.getDetailMovieById(movieId)

    fun getDetailTvShow(tvshowId: Int): LiveData<TvShowEntity> =
        catalogDao.getDetailTvShowById(tvshowId)

    fun insertMovies(movies: List<MovieEntitiy>) = catalogDao.insertMovies(movies)

    fun insertTvShow(tvshow: List<TvShowEntity>) = catalogDao.insertTvShow(tvshow)

    fun setFavoriteMovie(movie: MovieEntitiy) {
        movie.isFavorite = !movie.isFavorite
        catalogDao.updateMovie(movie)
    }

    fun setFavavoriteTvShow(tvshow: TvShowEntity) {
        tvshow.isFavorite = !tvshow.isFavorite
        catalogDao.updateTvShow(tvshow)
    }

}