package com.dicoding.submission_capstone.core.data.source

import com.dicoding.submission_capstone.core.data.source.remote.RemoteDataSource
import com.dicoding.submission_capstone.core.data.source.remote.network.ApiResponse
import com.dicoding.submission_capstone.core.data.source.remote.network.ApiService
import com.dicoding.submission_capstone.core.domain.model.DetailGame
import com.dicoding.submission_capstone.core.domain.model.Game
import com.dicoding.submission_capstone.core.domain.repository.IGamesRepository
import com.dicoding.submission_capstone.core.util.DataMapper
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class GameRepository @Inject constructor(private val remoteDataSource: RemoteDataSource): IGamesRepository {
    override fun getGames(): Flowable<Resource<List<Game>>> {
        val resultData = PublishSubject.create<Resource<List<Game>>>()

        resultData.onNext(Resource.Loading())
        val source = remoteDataSource.getGames();
        source.observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe{ data ->
                when (data) {
                    is ApiResponse.Success -> {
                        source.unsubscribeOn(Schedulers.io())
                        resultData.onNext(Resource.Success(data = data.data.map { DataMapper.gameResponseToGameData(it) }))
                    }
                    is ApiResponse.Error -> {
                        source.unsubscribeOn(Schedulers.io())
                        resultData.onNext(Resource.Error(data.errorMessage))
                    }
                    is ApiResponse.Empty -> {
                        source.unsubscribeOn(Schedulers.io())
                        resultData.onNext(Resource.Success(ArrayList()))
                    }
                }

            }


        return resultData.toFlowable(BackpressureStrategy.BUFFER)

    }

    override fun getDetailGame(id: Long): Flowable<Resource<DetailGame>> {
        val resultData = PublishSubject.create<Resource<DetailGame>>()

        resultData.onNext(Resource.Loading())
        val dataResponse = remoteDataSource.getDetailGame(id)
        dataResponse
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe {detailGameResponse ->
                when(detailGameResponse) {
                    is ApiResponse.Success -> {
                        dataResponse.unsubscribeOn(Schedulers.io())
                        resultData.onNext(Resource.Success(DataMapper.detailGameResponseToDetailGame(detailGameResponse.data)))
                    }
                    is ApiResponse.Empty -> {
                        dataResponse.unsubscribeOn(Schedulers.io())
                        resultData.onNext(Resource.Success(null))

                    }
                    is ApiResponse.Error -> {
                        dataResponse.unsubscribeOn(Schedulers.io())
                        resultData.onNext(Resource.Error(detailGameResponse.errorMessage))
                    }
                }
            }

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }


}