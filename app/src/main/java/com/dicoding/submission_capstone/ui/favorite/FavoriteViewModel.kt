package com.dicoding.submission_capstone.ui.favorite

import androidx.lifecycle.ViewModel
import com.dicoding.submission_capstone.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(gameUseCase: GameUseCase): ViewModel() {
}