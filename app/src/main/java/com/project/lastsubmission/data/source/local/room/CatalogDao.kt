package com.project.lastsubmission.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.project.lastsubmission.data.source.local.entitiy.MovieEntitiy
import com.project.lastsubmission.data.source.local.entitiy.TvShowEntity

@Dao
interface CatalogDao {

    @Query("SELECT * FROM tb_movie")
    fun getMovies(): DataSource.Factory<Int, MovieEntitiy>

    @Query("SELECT * FROM tb_tvshow")
    fun getTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tb_movie WHERE is_favorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntitiy>

    @Query("SELECT * FROM tb_tvshow WHERE is_favorite = 1")
    fun getFavoriteTvShow(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tb_movie WHERE movie_id = :movieId")
    fun getDetailMovieById(movieId: Int): LiveData<MovieEntitiy>

    @Query("SELECT * FROM tb_tvshow WHERE tvshow_id = :tvshowId")
    fun getDetailTvShowById(tvshowId: Int): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MovieEntitiy::class)
    fun insertMovies(movies: List<MovieEntitiy>)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = TvShowEntity::class)
    fun insertTvShow(tvshow: List<TvShowEntity>)

    @Update(entity = MovieEntitiy::class)
    fun updateMovie(movie: MovieEntitiy)

    @Update(entity = TvShowEntity::class)
    fun updateTvShow(tvshow: TvShowEntity)
}