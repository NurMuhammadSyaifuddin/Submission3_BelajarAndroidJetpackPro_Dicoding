package com.project.lastsubmission.ui.content.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.project.lastsubmission.data.source.CatalogRepository
import com.project.lastsubmission.data.source.local.entitiy.MovieEntitiy
import com.project.lastsubmission.data.source.local.entitiy.TvShowEntity
import org.junit.Assert
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogRepository: CatalogRepository

    @Mock
    private lateinit var observerMovie: Observer<PagedList<MovieEntitiy>>

    @Mock
    private lateinit var observerTvShow: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var moviePagedList: PagedList<MovieEntitiy>

    @Mock
    private lateinit var tvShowPagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(catalogRepository)
    }

    @Test
    fun getFavoriteMovie() {
        val dummyMovie = moviePagedList
        Mockito.`when`(dummyMovie.size).thenReturn(5)
        val movie = MutableLiveData<PagedList<MovieEntitiy>>()
        movie.value = dummyMovie

        Mockito.`when`(catalogRepository.getFavoriteMovies()).thenReturn(movie)
        val movieEntity = viewModel.getFavoriteMovie().value
        Mockito.verify(catalogRepository).getFavoriteMovies()
        Assert.assertNotNull(movieEntity)
        Assert.assertEquals(5, movieEntity?.size)

        viewModel.getFavoriteMovie().observeForever(observerMovie)
        Mockito.verify(observerMovie).onChanged(dummyMovie)
    }

    @Test
    fun getFavoriteTvShow() {
        val dummyTvShow = tvShowPagedList
        Mockito.`when`(dummyTvShow.size).thenReturn(5)
        val tvShow = MutableLiveData<PagedList<TvShowEntity>>()
        tvShow.value = dummyTvShow

        Mockito.`when`(catalogRepository.getFavoriteTvShow()).thenReturn(tvShow)
        val tvShowEntity = viewModel.getFavoriteTvShow().value
        Mockito.verify(catalogRepository).getFavoriteTvShow()
        Assert.assertNotNull(tvShowEntity)
        Assert.assertEquals(5, tvShowEntity?.size)

        viewModel.getFavoriteTvShow().observeForever(observerTvShow)
        Mockito.verify(observerTvShow).onChanged(dummyTvShow)
    }
}