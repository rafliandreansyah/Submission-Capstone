package com.dicoding.submission_capstone.core.data.source

import com.dicoding.submission_capstone.core.data.source.remote.RemoteDataSource
import com.dicoding.submission_capstone.core.data.source.remote.network.ApiResponse
import com.dicoding.submission_capstone.core.data.source.remote.network.ApiService
import com.dicoding.submission_capstone.core.domain.model.Game
import com.dicoding.submission_capstone.core.domain.repository.IGamesRepository
import com.dicoding.submission_capstone.core.util.DataMapper
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class GameRepository @Inject constructor(val remoteDataSource: RemoteDataSource): IGamesRepository {
    override fun getGames(): Flowable<Resource<List<Game>>> {
        val resultData = PublishSubject.create<Resource<List<Game>>>()

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
                        resultData.onNext(Resource.Success(ArrayList()))
                    }
                }

            }


        return resultData.toFlowable(BackpressureStrategy.BUFFER)

    }


}