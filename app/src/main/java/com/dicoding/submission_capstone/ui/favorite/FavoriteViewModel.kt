package com.dicoding.submission_capstone.ui.favorite

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.dicoding.submission_capstone.core.domain.model.DetailGame
import com.dicoding.submission_capstone.core.domain.model.Game
import com.dicoding.submission_capstone.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val gameUseCase: GameUseCase): ViewModel() {

    fun getListGameFavorite() = LiveDataReactiveStreams.fromPublisher(gameUseCase.getListFavoriteGame())

}