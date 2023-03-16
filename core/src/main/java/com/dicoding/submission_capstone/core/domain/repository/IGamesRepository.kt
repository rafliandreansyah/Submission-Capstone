package com.dicoding.submission_capstone.core.domain.repository

import com.dicoding.submission_capstone.core.data.source.Resource
import com.dicoding.submission_capstone.core.domain.model.DetailGame
import com.dicoding.submission_capstone.core.domain.model.Game
import io.reactivex.Flowable

interface IGamesRepository {

    fun getGames(): Flowable<Resource<List<Game>>>

    fun getDetailGame(id: Long): Flowable<Resource<DetailGame>>

    fun saveDetailGameToFavorite(detailGame: DetailGame)

    fun getFavoriteGames(): Flowable<List<Game>>

}