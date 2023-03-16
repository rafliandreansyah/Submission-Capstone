package com.dicoding.submission_capstone.ui.home

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.dicoding.submission_capstone.core.domain.model.Game
import com.dicoding.submission_capstone.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(gameUseCase: GameUseCase): ViewModel() {

    val dataListGame = LiveDataReactiveStreams.fromPublisher(gameUseCase.getGames())

}