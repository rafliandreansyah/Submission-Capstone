package com.dicoding.submission_capstone.core.domain.repository

import com.dicoding.submission_capstone.core.data.source.Resource
import com.dicoding.submission_capstone.core.data.source.remote.network.ApiResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.games.GameResponse
import com.dicoding.submission_capstone.core.domain.model.Game
import io.reactivex.Flowable

interface IGamesRepository {

    fun getGames(): Flowable<Resource<List<Game>>>

}