package com.dicoding.submission_capstone.core.data.source

import android.util.Log
import com.dicoding.submission_capstone.core.data.source.local.LocalDataSource
import com.dicoding.submission_capstone.core.data.source.remote.RemoteDataSource
import com.dicoding.submission_capstone.core.data.source.remote.network.ApiResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.detail_game.DetailGameResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.games.GameResponse
import com.dicoding.submission_capstone.core.domain.model.DetailGame
import com.dicoding.submission_capstone.core.domain.model.Game
import com.dicoding.submission_capstone.core.domain.repository.IGamesRepository
import com.dicoding.submission_capstone.core.util.DataMapper
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
        return object : NetworkBoundResource<DetailGame, DetailGameResponse>(){
            override fun loadFromDb(): Flowable<DetailGame> {
                return localDataSource.getDetailGame(id).map { data ->
                    DataMapper.gameWithDetailDataToDetailGame(data)
                }
            }

            override fun createCall(): Flowable<ApiResponse<DetailGameResponse>> {
                return remoteDataSource.getDetailGame(id)
            }

            override fun saveCallResult(data: DetailGameResponse) {
                localDataSource.insertDetailGameToDatabase(DataMapper.detailGameResponseToGameWithDetailData(data))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d(GameRepository::class.java.simpleName, "Berhasil saveCallResult")
                    }, {err ->
                        err.printStackTrace()
                        Log.e(GameRepository::class.java.simpleName, "Gagal saveCallResult ${err.message.toString()}")
                    })
            }

            override fun shouldFetch(data: DetailGame): Boolean {
                return true
            }

        }.asFlowable()
    }

    override fun saveDetailGameToFavorite(detailGame: DetailGame) {
        val data = DataMapper.detailGameToGameEntity(detailGame)
        localDataSource.updateFavoriteGame(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun getFavoriteGames(): Flowable<List<Game>> {
        return localDataSource.getListFavoriteGame().map { data ->
            DataMapper.gameWithPlatformsAndGenresToGame(data)
        }
    }


}