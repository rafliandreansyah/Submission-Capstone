package com.dicoding.submission_capstone.core.data.source

import android.provider.ContactsContract.Data
import android.util.Log
import com.dicoding.submission_capstone.core.data.source.local.LocalDataSource
import com.dicoding.submission_capstone.core.data.source.remote.RemoteDataSource
import com.dicoding.submission_capstone.core.data.source.remote.network.ApiResponse
import com.dicoding.submission_capstone.core.data.source.remote.network.ApiService
import com.dicoding.submission_capstone.core.data.source.remote.response.games.GameResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.games.ListGameResponse
import com.dicoding.submission_capstone.core.domain.model.DetailGame
import com.dicoding.submission_capstone.core.domain.model.Game
import com.dicoding.submission_capstone.core.domain.repository.IGamesRepository
import com.dicoding.submission_capstone.core.util.DataMapper
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class GameRepository @Inject constructor(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource): IGamesRepository {
    override fun getGames(): Flowable<Resource<List<Game>>> {
        return object : NetworkBoundResource<List<Game>, List<GameResponse>>(){
            override fun loadFromDb(): Flowable<List<Game>> {
                return localDataSource.getGames().map { data -> DataMapper.gameWithPlatformsAndGenresToGame(data) }
            }

            override fun createCall(): Flowable<ApiResponse<List<GameResponse>>> {
                return remoteDataSource.getGames()
            }

            override fun saveCallResult(data: List<GameResponse>) {
                val gameData = DataMapper.gameResponseToGameWithPlatformsAndGenres(data)
                localDataSource.insertListGameToDatabase(gameData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d(GameRepository::class.java.simpleName, "Berhasil saveCallResult")
                    }, {err ->
                        err.printStackTrace()
                        Log.e(GameRepository::class.java.simpleName, "Gagal saveCallResult ${err.message.toString()}")
                    })
            }

            override fun shouldFetch(data: List<Game>): Boolean {
                return true
            }

        }.asFlowable()

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