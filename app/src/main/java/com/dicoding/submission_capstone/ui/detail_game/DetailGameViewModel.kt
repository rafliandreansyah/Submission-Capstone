package com.dicoding.submission_capstone.ui.detail_game

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.dicoding.submission_capstone.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailGameViewModel @Inject constructor(private val useCase: GameUseCase): ViewModel() {

    private fun getDetailGame(id: String) = LiveDataReactiveStreams.fromPublisher(useCase.getDetailGames(id))

}