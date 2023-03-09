package com.dicoding.submission_capstone.di

import com.dicoding.submission_capstone.core.domain.usecase.GameInteractor
import com.dicoding.submission_capstone.core.domain.usecase.GameUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideGameUseCase(gameInteractor: GameInteractor): GameUseCase

}