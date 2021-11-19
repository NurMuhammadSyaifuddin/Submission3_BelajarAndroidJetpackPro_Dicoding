package com.project.lastsubmission.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.project.lastsubmission.data.source.CatalogRepository
import com.project.lastsubmission.data.source.local.entitiy.MovieEntitiy
import com.project.lastsubmission.data.source.local.entitiy.TvShowEntity
import com.project.lastsubmission.utils.DataDummy
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private val dummyMovie = DataDummy.generateDataMovieDummy()[0]
    private val movieId = dummyMovie.movieId
    private val dummyTvShow = DataDummy.generateDataTvShowDummy()[0]
    private val tvShowId = dummyTvShow.tvShowId

    private lateinit var viewModel: DetailViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogRepository: CatalogRepository

    @Mock
    private lateinit var observerMovie: Observer<MovieEntitiy>

    @Mock
    private lateinit var observerTvShow: Observer<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(catalogRepository)
    }

    @Test
    fun getDetailMovie() {
        val movieDummy = MutableLiveData<MovieEntitiy>()
        movieDummy.value = dummyMovie

        Mockito.`when`(catalogRepository.getDetailMovie(movieId)).thenReturn(movieDummy)

        val movieData = viewModel.getDetailMovie(movieId).value

        assertNotNull(movieData)
        assertEquals(dummyMovie.id, movieData?.id)
        assertEquals(dummyMovie.movieId, movieData?.movieId)
        assertEquals(dummyMovie.title, movieData?.title)
        assertEquals(dummyMovie.desc, movieData?.desc)
        assertEquals(dummyMovie.poster, movieData?.poster)
        assertEquals(dummyMovie.imgPreview, movieData?.imgPreview)

        viewModel.getDetailMovie(movieId).observeForever(observerMovie)
        verify(observerMovie).onChanged(dummyMovie)
    }

    @Test
    fun getDetailTvShow() {
        val tvShowDummy = MutableLiveData<TvShowEntity>()
        tvShowDummy.value = dummyTvShow

        Mockito.`when`(catalogRepository.getDetailTvShow(tvShowId)).thenReturn(tvShowDummy)

        val tvShowData = viewModel.getDetailTvShow(tvShowId).value

        assertNotNull(tvShowData)
        assertEquals(dummyTvShow.id, tvShowData?.id)
        assertEquals(dummyTvShow.tvShowId, tvShowData?.tvShowId)
        assertEquals(dummyTvShow.title, tvShowData?.title)
        assertEquals(dummyTvShow.desc, tvShowData?.desc)
        assertEquals(dummyTvShow.poster, tvShowData?.poster)
        assertEquals(dummyTvShow.imgPreview, tvShowData?.imgPreview)

        viewModel.getDetailTvShow(tvShowId).observeForever(observerTvShow)
        verify(observerTvShow).onChanged(dummyTvShow)
    }

}