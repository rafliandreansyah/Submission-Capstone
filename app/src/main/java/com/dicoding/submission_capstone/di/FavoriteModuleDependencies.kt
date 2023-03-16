package com.dicoding.submission_capstone.di

import com.dicoding.submission_capstone.core.domain.usecase.GameUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun gameUseCase(): GameUseCase

}