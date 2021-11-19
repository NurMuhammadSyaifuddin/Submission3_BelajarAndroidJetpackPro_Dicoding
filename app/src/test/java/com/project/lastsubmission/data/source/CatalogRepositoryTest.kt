package com.project.lastsubmission.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.project.lastsubmission.data.source.local.LocalDataSource
import com.project.lastsubmission.data.source.local.entitiy.MovieEntitiy
import com.project.lastsubmission.data.source.local.entitiy.TvShowEntity
import com.project.lastsubmission.data.source.remote.RemoteDataSource
import com.project.lastsubmission.utils.DataDummy
import com.project.lastsubmission.utils.LiveDataTestUtil
import com.project.lastsubmission.utils.PagedListUtil
import com.project.lastsubmission.vo.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito

class CatalogRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val catalogRepository = FakeCatalogRepository(remote, local)

    private val listMovie = DataDummy.generateDataMovieDummy()
    private val listTvShow = DataDummy.generateDataTvShowDummy()
    private val movie = listMovie[0]
    private val tvShow = listTvShow[0]

    @Test
    fun getMovies() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntitiy>
        Mockito.`when`(local.getMovies()).thenReturn(dataSourceFactory)
        catalogRepository.getMovies()

        val movieEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataMovieDummy()))
        Mockito.verify(local).getMovies()
        assertNotNull(movieEntity.data)
        assertEquals(listMovie.size, movieEntity.data?.size)
    }

    @Test
    fun getTvShow() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        Mockito.`when`(local.getTvShow()).thenReturn(dataSourceFactory)
        catalogRepository.getTvShow()

        val tvShowEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataTvShowDummy()))
        Mockito.verify(local).getTvShow()
        assertNotNull(tvShowEntity.data)
        assertEquals(listTvShow.size.toLong(), tvShowEntity.data?.size?.toLong())
    }

    @Test
    fun getFavoriteMovies() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntitiy>
        Mockito.`when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        catalogRepository.getFavoriteMovies()

        val movieEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataMovieDummy()))
        Mockito.verify(local).getFavoriteMovies()
        assertNotNull(movieEntity.data)
        assertEquals(listMovie.size.toLong(), movieEntity.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTvShow() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        Mockito.`when`(local.getFavoriteTvShow()).thenReturn(dataSourceFactory)
        catalogRepository.getFavoriteTvShow()

        val tvShowEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataTvShowDummy()))
        Mockito.verify(local).getFavoriteTvShow()
        assertNotNull(tvShowEntity.data)
        assertEquals(listTvShow.size.toLong(), tvShowEntity.data?.size?.toLong())
    }

    @Test
    fun getDetailMovie() {
        val dummyMovie = MutableLiveData<MovieEntitiy>()
        dummyMovie.value = movie
        Mockito.`when`(local.getDetailMovie(movie.movieId)).thenReturn(dummyMovie)

        val data = LiveDataTestUtil.getValue(catalogRepository.getDetailMovie(movie.movieId))
        Mockito.verify(local).getDetailMovie(movie.movieId)
        assertNotNull(data)
        assertEquals(movie.movieId, data.movieId)
    }

    @Test
    fun getDetailTvShow() {
        val dummyTvShow = MutableLiveData<TvShowEntity>()
        dummyTvShow.value = tvShow
        Mockito.`when`(local.getDetailTvShow(tvShow.tvShowId)).thenReturn(dummyTvShow)

        val data = LiveDataTestUtil.getValue(catalogRepository.getDetailTvShow(tvShow.tvShowId))
        Mockito.verify(local).getDetailTvShow(tvShow.tvShowId)
        assertNotNull(data)
        assertEquals(tvShow.tvShowId, data.tvShowId)
    }

}