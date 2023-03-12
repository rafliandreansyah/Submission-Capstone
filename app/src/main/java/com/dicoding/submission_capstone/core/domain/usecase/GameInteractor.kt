package com.dicoding.submission_capstone.core.domain.usecase

import com.dicoding.submission_capstone.core.data.source.Resource
import com.dicoding.submission_capstone.core.domain.model.DetailGame
import com.dicoding.submission_capstone.core.domain.model.Game
import com.dicoding.submission_capstone.core.domain.repository.IGamesRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GameInteractor @Inject constructor(private val gamesRepository: IGamesRepository): GameUseCase {

    override fun getGames(): Flowable<Resource<List<Game>>> = gamesRepository.getGames()
    override fun getDetailGames(id: Long): Flowable<Resource<DetailGame>> = gamesRepository.getDetailGame(id)

}