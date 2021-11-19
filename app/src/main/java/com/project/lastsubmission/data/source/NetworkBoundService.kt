package com.project.lastsubmission.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.project.lastsubmission.data.source.remote.vo.ApiResponse
import com.project.lastsubmission.data.source.remote.vo.StatusResponse
import com.project.lastsubmission.vo.Resource
import kotlinx.coroutines.*
import java.util.concurrent.Executors

abstract class NetworkBoundService<ResultType, RequestType> {

    private val result = MediatorLiveData<Resource<ResultType>>()

    private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatcher)

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDb()

        result.addSource(dbSource) {
            result.removeSource(dbSource)
            if (shouldFetchData(it)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(dbSource) {
            result.value = Resource.loading(it)
        }
        result.addSource(apiResponse) {
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (it.status) {
                StatusResponse.SUCCESS ->
                    scope.launch {
                        it.body?.let { data ->
                            saveCallResult(data)
                        }
                        Log.d("BOUND 1 : ", it.status.name)

                        withContext(Dispatchers.Main) {
                            result.addSource(loadFromDb()) { newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }
                StatusResponse.ERROR -> {
                    Log.d("BOUND 2 : ", it.status.name)
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.error(it.message, newData)
                    }
                }
            }
        }
    }

    protected abstract fun saveCallResult(data: RequestType)

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun shouldFetchData(data: ResultType?): Boolean

    protected abstract fun loadFromDb(): LiveData<ResultType>

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}