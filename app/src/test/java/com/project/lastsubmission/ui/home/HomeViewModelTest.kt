package com.project.lastsubmission.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.project.lastsubmission.data.source.CatalogRepository
import com.project.lastsubmission.data.source.local.entitiy.MovieEntitiy
import com.project.lastsubmission.data.source.local.entitiy.TvShowEntity
import com.project.lastsubmission.vo.Resource
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogRepository: CatalogRepository

    @Mock
    private lateinit var observerMovie: Observer<Resource<PagedList<MovieEntitiy>>>

    @Mock
    private lateinit var observerTvShow: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var moviePagedList: PagedList<MovieEntitiy>

    @Mock
    private lateinit var tvShowPagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = HomeViewModel(catalogRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovie = Resource.success(moviePagedList)
        Mockito.`when`(dummyMovie.data?.size).thenReturn(5)
        val movie = MutableLiveData<Resource<PagedList<MovieEntitiy>>>()
        movie.value = dummyMovie

        Mockito.`when`(catalogRepository.getMovies()).thenReturn(movie)
        val movieEntity = viewModel.getMovies().value?.data
        Mockito.verify(catalogRepository).getMovies()
        Assert.assertNotNull(movieEntity)
        Assert.assertEquals(5, movieEntity?.size)

        viewModel.getMovies().observeForever(observerMovie)
        Mockito.verify(observerMovie).onChanged(dummyMovie)
    }

    @Test
    fun getTvShow() {
        val dummyTvShow = Resource.success(tvShowPagedList)
        Mockito.`when`(dummyTvShow.data?.size).thenReturn(5)
        val tvShow = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tvShow.value = dummyTvShow

        Mockito.`when`(catalogRepository.getTvShow()).thenReturn(tvShow)
        val tvShowEntity = viewModel.getTvShow().value?.data
        Mockito.verify(catalogRepository).getTvShow()
        Assert.assertNotNull(tvShowEntity)
        Assert.assertEquals(5, tvShowEntity?.size)

        viewModel.getTvShow().observeForever(observerTvShow)
        Mockito.verify(observerTvShow).onChanged(dummyTvShow)
    }
}