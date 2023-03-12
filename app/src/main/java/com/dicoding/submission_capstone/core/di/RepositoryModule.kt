package com.dicoding.submission_capstone.core.di

import com.dicoding.submission_capstone.core.data.source.GameRepository
import com.dicoding.submission_capstone.core.domain.repository.IGamesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideGameRepository(gameRepository: GameRepository): IGamesRepository

}