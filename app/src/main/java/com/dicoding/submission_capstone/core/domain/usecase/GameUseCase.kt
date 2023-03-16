package com.dicoding.submission_capstone.core.domain.usecase

import com.dicoding.submission_capstone.core.data.source.Resource
import com.dicoding.submission_capstone.core.domain.model.DetailGame
import com.dicoding.submission_capstone.core.domain.model.Game
import io.reactivex.Flowable

interface GameUseCase {

    fun getGames(): Flowable<Resource<List<Game>>>

    fun getDetailGames(id: Long): Flowable<Resource<DetailGame>>

    fun updateGameDetailFavorite(detailGame: DetailGame)

    fun getListFavoriteGame(): Flowable<List<Game>>

}