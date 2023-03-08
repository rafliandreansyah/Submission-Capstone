package com.dicoding.submission_capstone.core.data.source

import com.dicoding.submission_capstone.core.data.source.remote.RemoteDataSource
import com.dicoding.submission_capstone.core.data.source.remote.network.ApiResponse
import com.dicoding.submission_capstone.core.data.source.remote.network.ApiService
import com.dicoding.submission_capstone.core.domain.model.Game
import com.dicoding.submission_capstone.core.domain.repository.IGamesRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GameRepository @Inject constructor(val remoteDataSource: RemoteDataSource): IGamesRepository {
    override fun getGames(): Flowable<ApiResponse<Resource<List<Game>>>> {
        TODO("Not yet implemented")
    }


}