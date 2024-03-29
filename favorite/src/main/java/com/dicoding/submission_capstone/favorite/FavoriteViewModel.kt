package com.dicoding.submission_capstone.favorite

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.dicoding.submission_capstone.core.domain.usecase.GameUseCase

class FavoriteViewModel(private val gameUseCase: GameUseCase): ViewModel() {

    fun getListGameFavorite() = LiveDataReactiveStreams.fromPublisher(gameUseCase.getListFavoriteGame())

}