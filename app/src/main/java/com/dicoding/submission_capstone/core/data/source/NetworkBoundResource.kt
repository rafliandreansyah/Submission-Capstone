package com.dicoding.submission_capstone.core.data.source

import android.util.Log
import com.dicoding.submission_capstone.core.data.source.remote.network.ApiResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = PublishSubject.create<Resource<ResultType>>()
    private val mCompositeDisposable = CompositeDisposable()

    init {
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        val db = dbSource
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe( { value ->
                dbSource.unsubscribeOn(Schedulers.io())
                if (shouldFetch(value)) {
                    fetchFromNetwork()
                }
                else {
                    result.onNext(Resource.Success(value))
                }
            }, {
                it.printStackTrace()
                Log.e(NetworkBoundResource::class.java.simpleName, "Error ${it.message}")
            })
        mCompositeDisposable.add(db)
    }

    protected open fun onFetchFailed(){}

    protected abstract fun loadFromDb(): Flowable<ResultType>

    protected abstract fun shouldFetch(data: ResultType): Boolean

    protected abstract fun createCall(): Flowable<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(){
        val apiResponse = createCall()
        result.onNext(Resource.Loading())

        val response = apiResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .doOnComplete {
                mCompositeDisposable.dispose()
            }.subscribe { response ->
                when(response) {
                    is ApiResponse.Success -> {
                        saveCallResult(response.data)
                        val dbSource = loadFromDb()
                        dbSource.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .take(1)
                            .subscribe { resultData ->
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.Success(resultData))
                            }
                    }
                    is ApiResponse.Empty -> {
                        val dbSource = loadFromDb()
                        dbSource.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .take(1)
                            .subscribe { resultData ->
                                dbSource.unsubscribeOn(Schedulers.io())
                                result.onNext(Resource.Success(resultData))
                            }
                    }
                    is ApiResponse.Error -> {
                        onFetchFailed()
                        result.onNext(Resource.Error(response.errorMessage))
                    }
                }
            }
        mCompositeDisposable.add(response)
    }

    fun asFlowable(): Flowable<Resource<ResultType>> = result.toFlowable(BackpressureStrategy.BUFFER)

}