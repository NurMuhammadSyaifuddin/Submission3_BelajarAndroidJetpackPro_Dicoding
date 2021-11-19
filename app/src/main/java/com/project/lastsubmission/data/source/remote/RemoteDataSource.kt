package com.project.lastsubmission.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.lastsubmission.data.source.remote.api.ApiService
import com.project.lastsubmission.data.source.remote.response.MovieResponse
import com.project.lastsubmission.data.source.remote.response.TvShowResponse
import com.project.lastsubmission.data.source.remote.vo.ApiResponse
import com.project.lastsubmission.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import retrofit2.await
import java.io.IOException
import java.util.concurrent.Executors
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatcher)

    fun getMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovieResponse = MutableLiveData<ApiResponse<List<MovieResponse>>>()

        scope.launch {
            try {
                val response = apiService.getMovies().await()
                resultMovieResponse.postValue(ApiResponse.success(response.result as List<MovieResponse>))
            } catch (e: IOException) {
                e.printStackTrace()
                resultMovieResponse.postValue(ApiResponse.error(e.message, mutableListOf()))
            }
        }
        EspressoIdlingResource.decrement()
        return resultMovieResponse
    }

    fun getTvShow(): LiveData<ApiResponse<List<TvShowResponse>>> {
        EspressoIdlingResource.increment()
        val resultTvShowResponse = MutableLiveData<ApiResponse<List<TvShowResponse>>>()

        scope.launch {
            try {
                val response = apiService.getTvShow().await()
                resultTvShowResponse.postValue(ApiResponse.success(response.result as List<TvShowResponse>))
            } catch (e: IOException) {
                e.printStackTrace()
                resultTvShowResponse.postValue(ApiResponse.error(e.message, mutableListOf()))
            }
        }
        EspressoIdlingResource.decrement()
        return resultTvShowResponse
    }


}