package com.dicoding.submission_capstone.ui.detail_game

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.dicoding.submission_capstone.core.domain.model.DetailGame
import com.dicoding.submission_capstone.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailGameViewModel @Inject constructor(private val gameUseCase: GameUseCase): ViewModel() {

    var idGame: Long = 0
    fun getDetailGame(id: Long) = LiveDataReactiveStreams.fromPublisher(gameUseCase.getDetailGames(id))

    fun saveToFavorite(detailGame: DetailGame) = gameUseCase.updateGameDetailFavorite(detailGame)

}