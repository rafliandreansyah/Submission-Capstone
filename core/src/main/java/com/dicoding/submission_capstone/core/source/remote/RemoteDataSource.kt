package com.dicoding.submission_capstone.core.data.source.remote

import com.dicoding.submission_capstone.core.data.source.remote.network.ApiResponse
import com.dicoding.submission_capstone.core.data.source.remote.network.ApiService
import com.dicoding.submission_capstone.core.data.source.remote.response.detail_game.DetailGameResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.games.GameResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {


    fun getGames(): Flowable<ApiResponse<List<GameResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<GameResponse>>>()

        val client = apiService.getGames()
        client
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({response ->
                val listGame = response.results
                resultData.onNext((if (listGame?.isNotEmpty() == true) ApiResponse.Success(listGame) else ApiResponse.Empty))
            }, { error ->
                error.printStackTrace()
                resultData.onNext(ApiResponse.Error(error.message.toString()))
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getDetailGame(id: Long): Flowable<ApiResponse<DetailGameResponse>> {
        val resultData = PublishSubject.create<ApiResponse<DetailGameResponse>>()

        val client = apiService.getDetailGame(id = id)
        client.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({detailGame ->
                resultData.onNext(if (detailGame != null) ApiResponse.Success(detailGame) else ApiResponse.Empty)
            }, {error ->
                error.printStackTrace()
                resultData.onNext(ApiResponse.Error(error.message.toString()))
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

}