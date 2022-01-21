package com.project.lastsubmission.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.project.lastsubmission.data.source.local.LocalDataSource
import com.project.lastsubmission.data.source.local.entitiy.MovieEntitiy
import com.project.lastsubmission.data.source.local.entitiy.TvShowEntity
import com.project.lastsubmission.data.source.remote.RemoteDataSource
import com.project.lastsubmission.data.source.remote.response.MovieResponse
import com.project.lastsubmission.data.source.remote.response.TvShowResponse
import com.project.lastsubmission.data.source.remote.vo.ApiResponse
import com.project.lastsubmission.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject

class FakeCatalogRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : CatalogDataSource {

    private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatcher)

    override fun getMovies(): LiveData<Resource<PagedList<MovieEntitiy>>> =
        object : NetworkBoundService<PagedList<MovieEntitiy>, List<MovieResponse>>() {
            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = mutableListOf<MovieEntitiy>()

                data.map {
                    movieList.add(
                        MovieEntitiy(
                            null,
                            it.id,
                            it.name,
                            it.desc,
                            it.poster,
                            it.imgPreview,
                            false
                        )
                    )
                }

                localDataSource.insertMovies(movieList)
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovies()

            override fun shouldFetchData(data: PagedList<MovieEntitiy>?): Boolean =
                data.isNullOrEmpty()


            override fun loadFromDb(): LiveData<PagedList<MovieEntitiy>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(4)
                    setPageSize(4)
                    build()
                }.build()

                return LivePagedListBuilder(localDataSource.getMovies(), config).build()
            }

        }.asLiveData()

    override fun getTvShow(): LiveData<Resource<PagedList<TvShowEntity>>> =
        object : NetworkBoundService<PagedList<TvShowEntity>, List<TvShowResponse>>() {
            override fun saveCallResult(data: List<TvShowResponse>) {
                val tvShowList = mutableListOf<TvShowEntity>()

                data.map {
                    tvShowList.add(
                        TvShowEntity(
                            null,
                            it.id,
                            it.name,
                            it.desc,
                            it.poster,
                            it.imgPreview,
                            false
                        )
                    )
                }

                localDataSource.insertTvShow(tvShowList)
            }

            override fun createCall(): LiveData<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getTvShow()

            override fun shouldFetchData(data: PagedList<TvShowEntity>?): Boolean =
                data.isNullOrEmpty()

            override fun loadFromDb(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(4)
                    setPageSize(4)
                }.build()
                return LivePagedListBuilder(localDataSource.getTvShow(), config).build()
            }

        }.asLiveData()

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntitiy>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getFavoriteTvShow(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()

        return LivePagedListBuilder(localDataSource.getFavoriteTvShow(), config).build()
    }

    override fun getDetailMovie(movieId: Int): LiveData<MovieEntitiy> =
        localDataSource.getDetailMovie(movieId)

    override fun getDetailTvShow(tvshowId: Int): LiveData<TvShowEntity> =
        localDataSource.getDetailTvShow(tvshowId)

    override fun setFavoriteMovie(movie: MovieEntitiy) {
        scope.launch {
            localDataSource.setFavoriteMovie(movie)
        }
    }

    override fun setFavoriteTvShow(tvshow: TvShowEntity) {
        scope.launch {
            localDataSource.setFavavoriteTvShow(tvshow)
        }
    }

}