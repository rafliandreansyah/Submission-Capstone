package com.dicoding.submission_capstone.core.di

import android.content.Context
import androidx.room.Room
import com.dicoding.submission_capstone.core.data.source.local.room.GameDao
import com.dicoding.submission_capstone.core.data.source.local.room.GameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesGameDatabase(@ApplicationContext context: Context): GameDatabase = Room.databaseBuilder(
        context,
        GameDatabase::class.java,
        "game-database"
    ).build()

    @Provides
    fun providesGameDao(gameDatabase: GameDatabase): GameDao = gameDatabase.gameDao()

}